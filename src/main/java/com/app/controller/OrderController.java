package com.app.controller;

import com.app.dto.OrderRequest;
import com.app.dto.OrderResponse;
import com.app.model.Order;
import com.app.service.OrderService;
import com.app.service.RewardsService;
import com.app.service.UserService;
import jakarta.validation.Valid;
lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for handling order creation.
 * Exposes POST /orders endpoint.
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final RewardsService rewardsService;
    private final OrderService orderService;
    private final UserService userService;

    /**
     * Creates a new order, evaluates rewards, saves the order, and updates user statistics.
     *
     * @param orderRequest the order request payload, validated
     * @return ResponseEntity with created order details and HTTP 201 status
     */
    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest orderRequest, BindingResult bindingResult) {
        // Handle validation errors
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            // 1. Evaluate rewards/discounts for the order/cart
            rewardsService.evaluateRewards(orderRequest);

            // 2. Save the order
            Order savedOrder = orderService.saveOrder(orderRequest);

            // 3. Update user statistics
            userService.updateUserStatistics(orderRequest.getUserId(), savedOrder);

            // 4. Build response DTO (adapt as needed)
            OrderResponse response = OrderResponse.fromOrder(savedOrder);

            // 5. Return created response
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException ex) {
            // Handle known business logic errors (e.g., invalid user, invalid items)
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getMessage());
            return ResponseEntity.badRequest().body(error);

        } catch (Exception ex) {
            // Handle unexpected server errors
            Map<String, String> error = new HashMap<>();
            error.put("error", "Internal server error. Please try again later.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Handles validation errors thrown by @Valid.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
