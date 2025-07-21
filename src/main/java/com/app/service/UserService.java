package com.app.service;

import com.app.model.User;
import com.app.model.Order;
import com.app.repository.UserRepository;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service layer for managing user-related operations.
 * Handles fetching user details and updating user statistics.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Fetches a user by their unique identifier.
     *
     * @param id the user ID
     * @return the User entity if found
     * @throws IllegalArgumentException if user is not found
     */
    public User getUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found."));
    }

    /**
     * Updates user statistics such as total orders and total spent.
     * This method should be called after an order is placed.
     *
     * @param user the user entity to update
     * @return the updated User entity
     */
    public User save(User user) {
        // Update user statistics logic here if needed before saving
        return userRepository.save(user);
    }

    /**
     * Updates user statistics based on a new order.
     * Increments totalOrders and adds to totalSpent.
     *
     * @param userId the user ID
     * @param order the order to use for updating statistics
     * @return the updated User entity
     */
    public User updateUserStatistics(String userId, Order order) {
        User user = getUser(userId);
        user.setTotalOrders(user.getTotalOrders() + 1);
        user.setTotalSpent(user.getTotalSpent() + order.getTotal());
        return userRepository.save(user);
    }
}
