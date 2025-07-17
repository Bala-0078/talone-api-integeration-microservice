package com.example.rewards.controller;

import com.example.rewards.model.RewardResponse;
import com.example.rewards.model.TalonOneSessionRequest;
import com.example.rewards.service.RewardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rewards")
public class RewardsController {

    private final RewardsService rewardsService;

    @Autowired
    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @Operation(summary = "Evaluate rewards and discounts for a cart", description = "Evaluates the current cart for personalized discounts and rewards using Talon.One.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rewards evaluated successfully", content = @Content(schema = @Schema(implementation = RewardResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/evaluate")
    public ResponseEntity<RewardResponse> evaluateRewards(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Cart and customer data for evaluation",
                    required = true,
                    content = @Content(schema = @Schema(implementation = TalonOneSessionRequest.class))
            )
            @RequestBody TalonOneSessionRequest request) {
        RewardResponse response = rewardsService.evaluateRewards(request);
        return ResponseEntity.ok(response);
    }
}
