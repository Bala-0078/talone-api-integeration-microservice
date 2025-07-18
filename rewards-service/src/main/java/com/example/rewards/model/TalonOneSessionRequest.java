package com.example.rewards.model;

import java.util.List;
import java.util.Map;

public class TalonOneSessionRequest {
    private String integrationId;
    private List<Map<String, Object>> cartItems;
    private Map<String, Object> attributes;

    public String getIntegrationId() { return integrationId; }
    public void setIntegrationId(String integrationId) { this.integrationId = integrationId; }
    public List<Map<String, Object>> getCartItems() { return cartItems; }
    public void setCartItems(List<Map<String, Object>> cartItems) { this.cartItems = cartItems; }
    public Map<String, Object> getAttributes() { return attributes; }
    public void setAttributes(Map<String, Object> attributes) { this.attributes = attributes; }
}
