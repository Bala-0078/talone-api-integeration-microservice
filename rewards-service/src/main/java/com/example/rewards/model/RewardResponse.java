package com.example.rewards.model;

import java.util.List;

public class RewardResponse {
    private String userId;
    private List<Discount> discounts;
    private List<Reward> rewards;

    public static class Discount {
        private String campaignId;
        private double discountAmount;
        public String getCampaignId() { return campaignId; }
        public void setCampaignId(String campaignId) { this.campaignId = campaignId; }
        public double getDiscountAmount() { return discountAmount; }
        public void setDiscountAmount(double discountAmount) { this.discountAmount = discountAmount; }
    }

    public static class Reward {
        private String rewardType;
        private int points;
        public String getRewardType() { return rewardType; }
        public void setRewardType(String rewardType) { this.rewardType = rewardType; }
        public int getPoints() { return points; }
        public void setPoints(int points) { this.points = points; }
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public List<Discount> getDiscounts() { return discounts; }
    public void setDiscounts(List<Discount> discounts) { this.discounts = discounts; }
    public List<Reward> getRewards() { return rewards; }
    public void setRewards(List<Reward> rewards) { this.rewards = rewards; }
}
