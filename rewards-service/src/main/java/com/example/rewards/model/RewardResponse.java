package com.example.rewards.model;

import java.math.BigDecimal;
import java.util.List;

public class RewardResponse {
    private BigDecimal totalDiscount;
    private List<RewardDetail> rewards;
    private List<DiscountDetail> discounts;
    private String message;

    public BigDecimal getTotalDiscount() { return totalDiscount; }
    public void setTotalDiscount(BigDecimal totalDiscount) { this.totalDiscount = totalDiscount; }
    public List<RewardDetail> getRewards() { return rewards; }
    public void setRewards(List<RewardDetail> rewards) { this.rewards = rewards; }
    public List<DiscountDetail> getDiscounts() { return discounts; }
    public void setDiscounts(List<DiscountDetail> discounts) { this.discounts = discounts; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public static class RewardDetail {
        private String type;
        private String description;
        private BigDecimal amount;

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
    }

    public static class DiscountDetail {
        private String code;
        private String description;
        private BigDecimal amount;

        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
    }
}
