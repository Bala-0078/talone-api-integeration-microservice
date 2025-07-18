package com.example.rewards.service;

import com.example.rewards.model.RewardResponse;
import com.example.rewards.model.TalonOneSessionRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RewardsService {
    @Autowired
    private TalonOneService talonOneService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public RewardResponse evaluateCart(String userId, List<Map<String, Object>> cartItems) {
        TalonOneSessionRequest sessionRequest = new TalonOneSessionRequest();
        sessionRequest.setIntegrationId(userId);
        sessionRequest.setCartItems(cartItems);
        sessionRequest.setAttributes(Map.of());
        ResponseEntity<String> talonResponse = talonOneService.evaluateSession(sessionRequest);
        RewardResponse response = new RewardResponse();
        response.setUserId(userId);
        List<RewardResponse.Discount> discounts = new ArrayList<>();
        List<RewardResponse.Reward> rewards = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(talonResponse.getBody());
            if (root.has("effects")) {
                for (JsonNode effect : root.get("effects")) {
                    if (effect.has("campaignId") && effect.has("props")) {
                        RewardResponse.Discount discount = new RewardResponse.Discount();
                        discount.setCampaignId(effect.get("campaignId").asText());
                        discount.setDiscountAmount(effect.get("props").path("value").asDouble(0));
                        discounts.add(discount);
                    }
                    if (effect.has("rewardType") && effect.has("points")) {
                        RewardResponse.Reward reward = new RewardResponse.Reward();
                        reward.setRewardType(effect.get("rewardType").asText());
                        reward.setPoints(effect.get("points").asInt(0));
                        rewards.add(reward);
                    }
                }
            }
        } catch (Exception e) {
            // ignore, return empty lists
        }
        response.setDiscounts(discounts);
        response.setRewards(rewards);
        return response;
    }
}
