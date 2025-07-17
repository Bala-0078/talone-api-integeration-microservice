package com.example.rewards.controller;

import com.example.rewards.model.RewardResponse;
import com.example.rewards.service.RewardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rewards")
@RequiredArgsConstructor
public class RewardsController {
    private final RewardsService rewardsService;

    @Operation(summary = "Evaluate cart session for discounts and rewards", description = "Evaluates the cart session using Talon.One and returns applicable discounts and rewards.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluation successful", content = @Content(schema = @Schema(implementation = RewardResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "502", description = "Talon.One API error", content = @Content)
    })
    @PostMapping(value = "/evaluate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RewardResponse> evaluateCart(@RequestBody(description = "Cart evaluation request", required = true, content = @Content(schema = @Schema(example = "{\"userId\":\"12345\",\"cartItems\":[{\"itemId\":\"A1\",\"quantity\":2,\"price\":50},{\"itemId\":\"B2\",\"quantity\":1,\"price\":100}],\"totalAmount\":200}")))
                                                           @org.springframework.web.bind.annotation.RequestBody Map<String, Object> request) {
        String userId = (String) request.get("userId");
        List<Map<String, Object>> cartItems = (List<Map<String, Object>>) request.get("cartItems");
        Double totalAmount = request.get("totalAmount") != null ? Double.valueOf(request.get("totalAmount").toString()) : 0.0;
        RewardResponse response = rewardsService.evaluateRewards(userId, cartItems, totalAmount);
        return ResponseEntity.ok(response);
    }
}
