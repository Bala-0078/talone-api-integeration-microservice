package com.example.rewards.controller;

import com.example.rewards.model.RewardResponse;
import com.example.rewards.service.RewardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rewards")
@Tag(name = "Rewards", description = "Endpoints for evaluating discounts and managing rewards.")
@Validated
public class RewardsController {
    @Autowired
    private RewardsService rewardsService;

    @Operation(summary = "Evaluate cart session", description = "Evaluates the cart session and returns applicable discounts and rewards.")
    @PostMapping(value = "/evaluate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RewardResponse> evaluateCart(@Valid @RequestBody CartRequest request) {
        RewardResponse response = rewardsService.evaluateCart(request.getUserId(), request.getCartItems());
        return ResponseEntity.ok(response);
    }

    public static class CartRequest {
        @NotBlank
        private String userId;
        private List<Map<String, Object>> cartItems;
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public List<Map<String, Object>> getCartItems() { return cartItems; }
        public void setCartItems(List<Map<String, Object>> cartItems) { this.cartItems = cartItems; }
    }
}
