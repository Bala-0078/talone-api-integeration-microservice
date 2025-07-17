package com.example.rewards.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Response containing evaluated rewards and discounts")
public class RewardResponse {
    @Schema(description = "Total discount applied", example = "10.50")
    private Double totalDiscount;

    @Schema(description = "List of applied rewards/discounts")
    private List<String> appliedRewards;

    @Schema(description = "Loyalty points awarded", example = "100")
    private Integer loyaltyPoints;

    // Getters and Setters
    public Double getTotalDiscount() {
        return totalDiscount;
    }
    public void setTotalDiscount(Double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }
    public List<String> getAppliedRewards() {
        return appliedRewards;
    }
    public void setAppliedRewards(List<String> appliedRewards) {
        this.appliedRewards = appliedRewards;
    }
    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }
    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
}
