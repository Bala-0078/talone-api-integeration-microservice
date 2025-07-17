package com.example.rewards.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;

@Schema(description = "Request payload for evaluating cart and customer session with Talon.One")
public class TalonOneSessionRequest {
    @Schema(description = "Customer ID", example = "CUST789")
    private String customerId;

    @Schema(description = "Cart items with productId and quantity")
    private List<Map<String, Object>> cartItems;

    @Schema(description = "Additional session attributes")
    private Map<String, Object> attributes;

    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public List<Map<String, Object>> getCartItems() {
        return cartItems;
    }
    public void setCartItems(List<Map<String, Object>> cartItems) {
        this.cartItems = cartItems;
    }
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
