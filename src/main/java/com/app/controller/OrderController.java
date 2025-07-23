package com.app.controller;

import com.app.model.OrderRequest;
import com.app.model.Order;
import com.app.model.RewardsResponse;
import com.app.service.OrderService;
import com.app.service.RewardsService;
import com.app.service.UserService;
lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * Controller for order-related operations.
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;
    private final RewardsService rewardsService;
    private final UserService userService;

    /**
     * Place a new order, evaluate rewards, save order, and update user stats.
     * @param orderRequest Order request payload
     * @return Created order
     */
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody @Valid OrderRequest orderRequest) {
        // Evaluate rewards for this order (cart)
        RewardsResponse rewards = rewardsService.evaluateRewards(orderRequest.getCartRequest());
        // Save order and update user stats
        Order order = orderService.placeOrder(orderRequest, rewards);
        userService.updateUserStats(order.getUserId(), order.getUserStats());
        return ResponseEntity.status(201).body(order);
    }
}
