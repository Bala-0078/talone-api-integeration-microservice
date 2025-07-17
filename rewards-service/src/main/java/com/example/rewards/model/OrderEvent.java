package com.example.rewards.model;

import java.util.List;

/**
 * Model representing an order event received from Kafka.
 */
public class OrderEvent {
    private String orderId;
    private String customerId;
    private double totalAmount;
    private List<CartItem> items;

    // Getters and setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }

    /**
     * Inner class representing a cart item.
     */
    public static class CartItem {
        private String sku;
        private int quantity;
        private double price;
        public String getSku() { return sku; }
        public void setSku(String sku) { this.sku = sku; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
    }
}
