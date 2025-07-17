package com.example.orderservice.service;

import com.example.orderservice.dto.CartItemDto;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.OrderException;
import com.example.orderservice.feign.RewardsServiceClient;
import com.example.orderservice.feign.UserServiceClient;
import com.example.orderservice.kafka.OrderEventPublisher;
import com.example.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserServiceClient userServiceClient;
    private final RewardsServiceClient rewardsServiceClient;
    private final OrderEventPublisher orderEventPublisher;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        // Validate user
        Object user = userServiceClient.getUserById(orderRequest.getUserId());
        if (user == null) {
            throw new OrderException("User not found");
        }

        // Prepare order info for rewards-service
        Map<String, Object> orderInfo = new HashMap<>();
        orderInfo.put("userId", orderRequest.getUserId());
        orderInfo.put("cartItems", orderRequest.getCartItems());
        orderInfo.put("totalAmount", orderRequest.getTotalAmount());

        // Call rewards-service for discount
        Map<String, Object> discountResponse = rewardsServiceClient.calculateDiscount(orderInfo);
        BigDecimal discount = new BigDecimal(discountResponse.getOrDefault("discount", 0).toString());

        // Serialize cartItems to JSON
        String cartItemsJson;
        try {
            cartItemsJson = objectMapper.writeValueAsString(orderRequest.getCartItems());
        } catch (JsonProcessingException e) {
            throw new OrderException("Failed to serialize cart items");
        }

        // Create and save order
        Order order = Order.builder()
                .userId(orderRequest.getUserId())
                .cartItems(cartItemsJson)
                .totalAmount(orderRequest.getTotalAmount())
                .discount(discount)
                .build();
        order = orderRepository.save(order);

        // Publish event to Kafka
        orderEventPublisher.publishOrderEvent(order);

        // Build response
        List<CartItemDto> cartItems;
        try {
            cartItems = objectMapper.readValue(order.getCartItems(), objectMapper.getTypeFactory().constructCollectionType(List.class, CartItemDto.class));
        } catch (JsonProcessingException e) {
            throw new OrderException("Failed to deserialize cart items");
        }
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .cartItems(cartItems)
                .totalAmount(order.getTotalAmount())
                .discount(order.getDiscount())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
