package com.example.rewards.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private String eventType;
    private String orderId;
    private String userId;
    private List<LoyaltyAction> loyaltyActions;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoyaltyAction {
        private String actionType;
        private Integer points;
    }
}
