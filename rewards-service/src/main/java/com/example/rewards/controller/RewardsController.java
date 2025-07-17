package com.example.rewards.controller;

import com.example.rewards.model.RewardResponse;
import com.example.rewards.model.TalonOneSessionRequest;
import com.example.rewards.service.RewardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for rewards and discounts endpoints.
 */
@RestController
@RequestMapping("/api/rewards")
public class RewardsController {
    private static final Logger logger = LoggerFactory.getLogger(RewardsController.class);

    @Autowired
    private RewardsService rewardsService;

    @Operation(summary = "Evaluate cart for discounts and rewards", description = "Evaluates the cart using Talon.One API and returns applicable discounts and rewards.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rewards evaluated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RewardResponse.class),
                            examples = @ExampleObject(value = "{\n  \"discount\": 10.0,\n  \"rewardPoints\": 100,\n  \"message\": \"10% discount applied. 100 points awarded.\"\n}"))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"error\": \"Invalid cart data\"\n}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"error\": \"Failed to evaluate rewards\"\n}")))
    })
    @PostMapping("/evaluate")
    public ResponseEntity<RewardResponse> evaluateCart(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Cart data for evaluation",
            required = true,
            content = @Content(schema = @Schema(implementation = TalonOneSessionRequest.class),
                    examples = @ExampleObject(value = "{\n  \"customerId\": \"12345\",\n  \"cartItems\": [\n    {\n      \"sku\": \"SKU123\",\n      \"quantity\": 2,\n      \"price\": 50.0\n    }\n  ]\n}")))
            @RequestBody TalonOneSessionRequest sessionRequest) {
        logger.info("Received cart evaluation request for customerId: {}", sessionRequest.getCustomerId());
        RewardResponse response = rewardsService.evaluateCart(sessionRequest);
        return ResponseEntity.ok(response);
    }
}
