package com.app.service;

import com.app.model.CartRequest;
import com.app.model.ProfileDTO;
import com.app.model.SessionDTO;
import com.app.model.RewardsResponse;
import com.app.talonone.TalonOneClient;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service layer for handling rewards and discount logic.
 * Integrates with Talon.One API for personalized rewards.
 */
@Service
@RequiredArgsConstructor
public class RewardsService {

    private final TalonOneClient talonOneClient;

    /**
     * Evaluates the cart for applicable rewards and discounts using Talon.One.
     * Updates the user profile and evaluates the session.
     *
     * @param req the cart request payload
     * @return RewardsResponse containing applicable discounts and rewards
     */
    public RewardsResponse evaluateCart(CartRequest req) {
        // 1. Update user profile in Talon.One
        ProfileDTO profile = new ProfileDTO(req.getUserId());
        talonOneClient.updateProfile(profile);

        // 2. Evaluate session for discounts/rewards
        SessionDTO session = new SessionDTO(req.getUserId(), req.getItems());
        RewardsResponse response = talonOneClient.evaluateSession(session);

        return response;
    }

    /**
     * Confirms loyalty point usage for a user after order placement.
     *
     * @param userId the user ID
     * @param total the total amount spent in the order
     */
    public void confirmLoyalty(String userId, double total) {
        talonOneClient.confirmLoyalty(userId, total);
    }

    /**
     * Adapter for controller compatibility: evaluates rewards for an order request.
     * (Optional helper for controller integration.)
     *
     * @param orderRequest the order request
     * @return RewardsResponse for the order
     */
    public RewardsResponse evaluateRewards(CartRequest orderRequest) {
        return evaluateCart(orderRequest);
    }
}
