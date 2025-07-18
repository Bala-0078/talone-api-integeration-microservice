package com.example.rewards.model;

import java.util.List;

public class OrderEvent {
    private String eventType;
    private String orderId;
    private String userId;
    private List<LoyaltyAction> loyaltyActions;

    public static class LoyaltyAction {
        private String actionType;
        private int points;
        // Getters and setters
        public String getActionType() { return actionType; }
        public void setActionType(String actionType) { this.actionType = actionType; }
        public int getPoints() { return points; }
        public void setPoints(int points) { this.points = points; }
    }

    // Getters and setters
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public List<LoyaltyAction> getLoyaltyActions() { return loyaltyActions; }
    public void setLoyaltyActions(List<LoyaltyAction> loyaltyActions) { this.loyaltyActions = loyaltyActions; }
}
