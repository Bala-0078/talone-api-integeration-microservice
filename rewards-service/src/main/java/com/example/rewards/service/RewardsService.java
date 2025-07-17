package com.example.rewards.service;

import com.example.rewards.model.RewardResponse;
import com.example.rewards.model.TalonOneSessionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardsService {
    private final TalonOneService talonOneService;

    @Autowired
    public RewardsService(TalonOneService talonOneService) {
        this.talonOneService = talonOneService;
    }

    public RewardResponse evaluateRewards(TalonOneSessionRequest request) {
        // Additional business logic can be added here before/after calling Talon.One
        return talonOneService.evaluateSession(request);
    }
}
