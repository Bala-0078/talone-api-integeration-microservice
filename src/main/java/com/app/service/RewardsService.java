package com.app.service;

import com.app.model.CartRequest;
import com.app.model.RewardsResponse;
import com.app.talonone.TalonOneClient;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for rewards evaluation and Talon.One API integration.
 */
@Service
@RequiredArgsConstructor
public class RewardsService {

    private final TalonOneClient talonOneClient;

    /**
     * Evaluates rewards for a given cart using Talon.One Integration API.
     * @param cartRequest Cart details
     * @return RewardsResponse containing discounts and applied rewards
     */
    public RewardsResponse evaluateRewards(CartRequest cartRequest) {
        // Update user profile in Talon.One
        talonOneClient.updateProfile(cartRequest.getUserId(), cartRequest.getProfileDTO());

        // Evaluate session (cart) in Talon.One
        return talonOneClient.evaluateSession(cartRequest);
    }

    /**
     * Confirms loyalty point usage for a user after order placement.
     * @param userId The user ID
     * @param total The total amount spent
     */
    public void confirmLoyalty(String userId, double total) {
        talonOneClient.confirmLoyalty(userId, total);
    }
}
