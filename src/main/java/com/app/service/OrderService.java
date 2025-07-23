package com.app.service;

import com.app.model.Order;
import com.app.model.OrderRequest;
import com.app.model.CartRequest;
import com.app.model.RewardsResponse;
import com.app.repository.OrderRepository;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

/**
 * Service for order-related business logic.
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;
    private final RewardsService rewardsService;
    private final OrderRepository orderRepository;

    /**
     * Places a new order, evaluates rewards, applies discounts, saves order, and updates user stats.
     * @param orderRequest The order request payload
     * @param rewardsResponse The evaluated rewards for the order/cart
     * @return The created Order entity
     */
    public Order placeOrder(OrderRequest orderRequest, RewardsResponse rewardsResponse) {
        // Retrieve user
        Long userId = orderRequest.getUserId();
        var user = userService.getUserById(userId);

        // Evaluate cart and apply rewards (discounts, loyalty, etc.)
        CartRequest cartRequest = orderRequest.getCartRequest();
        // rewardsResponse is already provided by controller

        // Calculate final total after applying discount
        double originalTotal = cartRequest.getTotal();
        double discount = rewardsResponse.getDiscountAmount();
        double finalTotal = Math.max(0, originalTotal - discount);

        // Create and save order
        Order order = new Order();
        order.setUserId(userId);
        order.setItems(cartRequest.getItems());
        order.setTotal(finalTotal);
        order.setDiscount(discount);
        order.setRewardsApplied(rewardsResponse.getAppliedRewards());
        orderRepository.save(order);

        // Update user stats (increment totalOrders, add to totalSpent)
        user.setTotalOrders(user.getTotalOrders() + 1);
        user.setTotalSpent(user.getTotalSpent() + finalTotal);
        userService.updateUserStats(userId, user.toProfileDTO());

        // Confirm loyalty point usage if applicable
        rewardsService.confirmLoyalty(userId.toString(), finalTotal);

        return order;
    }
}
