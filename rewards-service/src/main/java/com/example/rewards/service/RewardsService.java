package com.example.rewards.service;

import com.example.rewards.model.RewardResponse;
import com.example.rewards.model.TalonOneSessionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RewardsService {
    private final TalonOneService talonOneService;

    public RewardResponse evaluateRewards(String userId, List<Map<String, Object>> cartItems, Double totalAmount) {
        TalonOneSessionRequest sessionRequest = TalonOneSessionRequest.builder()
                .integrationId(userId)
                .cartItems(cartItems)
                .totalAmount(totalAmount)
                .build();
        Map<String, Object> talonResponse = talonOneService.evaluateSession(sessionRequest);
        // Parse discounts and rewards from Talon.One response
        List<Map<String, Object>> discounts = (List<Map<String, Object>>) talonResponse.getOrDefault("discounts", Collections.emptyList());
        List<Map<String, Object>> rewards = (List<Map<String, Object>>) talonResponse.getOrDefault("rewards", Collections.emptyList());
        return RewardResponse.builder()
                .userId(userId)
                .discounts(discounts)
                .rewards(rewards)
                .build();
    }
}
