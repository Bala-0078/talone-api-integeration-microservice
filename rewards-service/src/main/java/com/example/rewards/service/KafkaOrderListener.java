package com.example.rewards.service;

import com.example.rewards.model.OrderEvent;
import com.example.rewards.model.TalonOneSessionRequest;
import com.example.rewards.model.RewardResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Kafka listener for order events.
 */
@Service
public class KafkaOrderListener {
    private static final Logger logger = LoggerFactory.getLogger(KafkaOrderListener.class);

    @Autowired
    private RewardsService rewardsService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Listen to order events from Kafka and process rewards.
     * Sample event log:
     * {
     *   "orderId": "ORD123",
     *   "customerId": "12345",
     *   "totalAmount": 100.0,
     *   "items": [ { "sku": "SKU123", "quantity": 2, "price": 50.0 } ]
     * }
     */
    @KafkaListener(topics = "order-events", groupId = "rewards-service")
    public void listen(String message) {
        try {
            OrderEvent orderEvent = objectMapper.readValue(message, OrderEvent.class);
            logger.info("Received order event: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(orderEvent));
            // Build TalonOneSessionRequest from orderEvent
            TalonOneSessionRequest sessionRequest = TalonOneSessionRequest.fromOrderEvent(orderEvent);
            RewardResponse response = rewardsService.evaluateCart(sessionRequest);
            logger.info("Reward evaluation result for orderId {}: {}", orderEvent.getOrderId(), objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
        } catch (Exception ex) {
            logger.error("Failed to process order event: {}", ex.getMessage(), ex);
        }
    }
}
