package com.example.rewards.model;

/**
 * Model representing the response for rewards and discounts.
 * Example JSON:
 * {
 *   "discount": 10.0,
 *   "rewardPoints": 100,
 *   "message": "10% discount applied. 100 points awarded."
 * }
 */
public class RewardResponse {
    private double discount;
    private int rewardPoints;
    private String message;

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }
    public int getRewardPoints() { return rewardPoints; }
    public void setRewardPoints(int rewardPoints) { this.rewardPoints = rewardPoints; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
