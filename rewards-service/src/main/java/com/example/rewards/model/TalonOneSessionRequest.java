package com.example.rewards.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model representing the request to Talon.One API for session evaluation.
 * Example JSON:
 * {
 *   "customerId": "12345",
 *   "cartItems": [
 *     { "sku": "SKU123", "quantity": 2, "price": 50.0 }
 *   ]
 * }
 */
public class TalonOneSessionRequest {
    private String customerId;
    private List<CartItem> cartItems;

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public List<CartItem> getCartItems() { return cartItems; }
    public void setCartItems(List<CartItem> cartItems) { this.cartItems = cartItems; }

    /**
     * Helper to build from OrderEvent.
     */
    public static TalonOneSessionRequest fromOrderEvent(OrderEvent orderEvent) {
        TalonOneSessionRequest req = new TalonOneSessionRequest();
        req.setCustomerId(orderEvent.getCustomerId());
        List<CartItem> items = new ArrayList<>();
        if (orderEvent.getItems() != null) {
            for (OrderEvent.CartItem i : orderEvent.getItems()) {
                CartItem ci = new CartItem();
                ci.setSku(i.getSku());
                ci.setQuantity(i.getQuantity());
                ci.setPrice(i.getPrice());
                items.add(ci);
            }
        }
        req.setCartItems(items);
        return req;
    }

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
