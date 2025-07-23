package com.app.controller;

import com.app.model.CartRequest;
import com.app.model.RewardsResponse;
import com.app.service.RewardsService;
lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * Controller for rewards evaluation and integration with Talon.One.
 */
@RestController
@RequestMapping("/rewards")
@RequiredArgsConstructor
@Validated
public class RewardsController {

    private final RewardsService rewardsService;

    /**
     * Evaluate rewards for a given cart using Talon.One Integration API.
     * @param cartRequest Cart details
     * @return Rewards response
     */
    @PostMapping("/evaluate")
    public ResponseEntity<RewardsResponse> evaluateRewards(@RequestBody @Valid CartRequest cartRequest) {
        RewardsResponse rewards = rewardsService.evaluateRewards(cartRequest);
        return ResponseEntity.ok(rewards);
    }
}
