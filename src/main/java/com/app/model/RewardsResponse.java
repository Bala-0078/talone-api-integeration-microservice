package com.app.model;

import lombok.*;
import java.util.List;

/**
 * DTO representing the response from Talon.One rewards evaluation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RewardsResponse {

    private double discountAmount;

    private List<String> appliedRewards; // e.g., list of applied coupon codes or reward names

    private int loyaltyPointsUsed;

    private int loyaltyPointsEarned;
}
