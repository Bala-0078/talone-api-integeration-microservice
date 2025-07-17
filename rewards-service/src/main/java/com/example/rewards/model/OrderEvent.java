package com.example.rewards.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Order event received from Kafka")
public class OrderEvent {
    @Schema(description = "Unique order ID", example = "ORD123456")
    private String orderId;

    @Schema(description = "Customer ID", example = "CUST789")
    private String customerId;

    @Schema(description = "Order total amount", example = "99.99")
    private Double totalAmount;

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public Double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
