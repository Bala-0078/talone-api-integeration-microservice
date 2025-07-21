package com.app.service;

import com.app.model.Order;
import com.app.model.User;
import com.app.model.OrderRequest;
import com.app.model.CartRequest;
import com.app.model.RewardsResponse;
import com.app.repository.OrderRepository;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service layer for handling order processing logic.
 * Responsible for placing orders, applying discounts, and updating user stats.
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;
    private final RewardsService rewardsService;
    private final OrderRepository orderRepository;

    /**
     * Places an order for a user, evaluates discounts, saves the order,
     * updates user statistics, and confirms loyalty point usage.
     *
     * @param req the order request payload
     * @return the created Order entity
     */
    public Order placeOrder(OrderRequest req) {
        // 1. Retrieve user
        User user = userService.getUser(req.getUserId());

        // 2. Evaluate discounts/rewards
        CartRequest cartRequest = new CartRequest(req.getUserId(), req.getItems());
        RewardsResponse rewards = rewardsService.evaluateCart(cartRequest);

        // 3. Calculate final total after discounts
        double discount = rewards.getTotalDiscount();
        double originalTotal = req.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        double finalTotal = Math.max(0, originalTotal - discount);

        // 4. Create and save order
        Order order = new Order();
        order.setUserId(req.getUserId());
        order.setItems(req.getItems());
        order.setDiscount(discount);
        order.setTotal(finalTotal);
        order.setStatus("PLACED");
        Order savedOrder = orderRepository.save(order);

        // 5. Update user statistics
        user.setTotalOrders(user.getTotalOrders() + 1);
        user.setTotalSpent(user.getTotalSpent() + finalTotal);
        userService.save(user);

        // 6. Confirm loyalty point usage if applicable
        rewardsService.confirmLoyalty(req.getUserId(), finalTotal);

        return savedOrder;
    }

    /**
     * Saves an order entity directly.
     * (For use cases where order is already constructed and discounts applied.)
     *
     * @param order the order to save
     * @return the saved Order entity
     */
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
