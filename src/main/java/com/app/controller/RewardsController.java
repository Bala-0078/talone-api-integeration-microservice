package com.app.controller;

import com.app.dto.CartRequest;
import com.app.dto.RewardsResponse;
import com.app.service.RewardsService;
import jakarta.validation.Valid;
lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller responsible for handling reward evaluation requests.
 * Exposes endpoints under /rewards.
 */
@RestController
@RequestMapping("/rewards")
@RequiredArgsConstructor
public class RewardsController {

    private final RewardsService rewardsService;

    /**
     * Evaluates rewards and discounts for the given cart.
     *
     * @param cartRequest the cart details, validated
     * @return RewardsResponse containing applicable rewards and discounts
     */
    @PostMapping("/evaluate")
    public ResponseEntity<RewardsResponse> evaluateRewards(
            @Valid @RequestBody CartRequest cartRequest) {
        // Delegate to the service layer to evaluate rewards
        RewardsResponse response = rewardsService.evaluateRewards(cartRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Handles validation errors and returns a structured response with error details.
     *
     * @param ex the MethodArgumentNotValidException thrown on validation failure
     * @return ResponseEntity with error details and HTTP 400 status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles generic exceptions from the service layer and returns a structured error response.
     *
     * @param ex the Exception thrown during processing
     * @return ResponseEntity with error message and HTTP 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleServiceExceptions(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "An unexpected error occurred: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
