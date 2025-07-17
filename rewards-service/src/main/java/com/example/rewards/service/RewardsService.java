package com.example.rewards.service;

import com.example.rewards.model.RewardResponse;
import com.example.rewards.model.TalonOneSessionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for rewards business logic.
 */
@Service
public class RewardsService {
    private static final Logger logger = LoggerFactory.getLogger(RewardsService.class);

    @Autowired
    private TalonOneService talonOneService;

    /**
     * Evaluate cart and return rewards/discounts.
     * @param sessionRequest TalonOneSessionRequest
     * @return RewardResponse
     */
    public RewardResponse evaluateCart(TalonOneSessionRequest sessionRequest) {
        logger.info("Evaluating cart for customerId: {}", sessionRequest.getCustomerId());
        RewardResponse response = talonOneService.evaluateSession(sessionRequest);
        // Additional business logic can be added here if needed
        return response;
    }
}
