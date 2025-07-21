package com.app.talonone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import com.app.dto.ProfileDTO;
import com.app.dto.SessionDTO;
import com.app.dto.RewardsResponse;

/**
 * TalonOneClient is a reusable, centralized client for interacting with the Talon.One Integration API.
 * It handles HTTP communication, authentication headers, and provides methods for updating user profiles,
 * evaluating sessions for rewards, and confirming loyalty points.
 *
 * <p>Configuration:
 * <ul>
 *     <li>talonone.base-url - The base URL for the Talon.One API (set in application.properties)</li>
 *     <li>talonone.api-key - The API key for authenticating requests (set in application.properties)</li>
 * </ul>
 *
 * <p>Usage:
 * <pre>
 *     {@code
 *     @Autowired
 *     private TalonOneClient talonOneClient;
 *     
 *     talonOneClient.updateProfile("user123", profileDto);
 *     RewardsResponse response = talonOneClient.evaluateSession(sessionDto);
 *     talonOneClient.confirmLoyalty("user123", 150.0);
 *     }
 * </pre>
 */
@Component
public class TalonOneClient {

    private final WebClient webClient;
    private final String baseUrl;
    private final String apiKey;

    /**
     * Constructs a TalonOneClient with injected configuration.
     *
     * @param baseUrl the base URL for the Talon.One API
     * @param apiKey the API key for authentication
     */
    public TalonOneClient(
            @Value("${talonone.base-url}") String baseUrl,
            @Value("${talonone.api-key}") String apiKey
    ) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * Updates a user profile in Talon.One.
     *
     * @param userId the unique identifier of the user
     * @param dto the profile data to update
     * @throws TalonOneClientException if the API call fails
     */
    public void updateProfile(String userId, ProfileDTO dto) {
        try {
            webClient.put()
                    .uri("/v1/profiles/{userId}", userId)
                    .bodyValue(dto)
                    .retrieve()
                    .onStatus(HttpStatus::isError, response ->
                            response.bodyToMono(String.class)
                                    .defaultIfEmpty("Unknown error")
                                    .flatMap(msg -> Mono.error(new TalonOneClientException(
                                            "Failed to update profile: " + msg,
                                            response.statusCode().value()
                                    )))
                    )
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException ex) {
            throw new TalonOneClientException("Failed to update profile: " + ex.getResponseBodyAsString(), ex.getRawStatusCode(), ex);
        } catch (Exception ex) {
            throw new TalonOneClientException("Unexpected error updating profile", ex);
        }
    }

    /**
     * Evaluates a session for rewards and discounts in Talon.One.
     *
     * @param dto the session data to evaluate
     * @return RewardsResponse containing applicable rewards and discounts
     * @throws TalonOneClientException if the API call fails
     */
    public RewardsResponse evaluateSession(SessionDTO dto) {
        try {
            return webClient.post()
                    .uri("/v1/sessions")
                    .bodyValue(dto)
                    .retrieve()
                    .onStatus(HttpStatus::isError, response ->
                            response.bodyToMono(String.class)
                                    .defaultIfEmpty("Unknown error")
                                    .flatMap(msg -> Mono.error(new TalonOneClientException(
                                            "Failed to evaluate session: " + msg,
                                            response.statusCode().value()
                                    )))
                    )
                    .bodyToMono(RewardsResponse.class)
                    .block();
        } catch (WebClientResponseException ex) {
            throw new TalonOneClientException("Failed to evaluate session: " + ex.getResponseBodyAsString(), ex.getRawStatusCode(), ex);
        } catch (Exception ex) {
            throw new TalonOneClientException("Unexpected error evaluating session", ex);
        }
    }

    /**
     * Confirms loyalty point usage for a user after order placement in Talon.One.
     *
     * @param userId the unique identifier of the user
     * @param totalAmount the total order amount to confirm
     * @throws TalonOneClientException if the API call fails
     */
    public void confirmLoyalty(String userId, double totalAmount) {
        try {
            webClient.post()
                    .uri("/v1/loyalty/{userId}/confirm", userId)
                    .bodyValue(new LoyaltyConfirmRequest(totalAmount))
                    .retrieve()
                    .onStatus(HttpStatus::isError, response ->
                            response.bodyToMono(String.class)
                                    .defaultIfEmpty("Unknown error")
                                    .flatMap(msg -> Mono.error(new TalonOneClientException(
                                            "Failed to confirm loyalty: " + msg,
                                            response.statusCode().value()
                                    )))
                    )
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException ex) {
            throw new TalonOneClientException("Failed to confirm loyalty: " + ex.getResponseBodyAsString(), ex.getRawStatusCode(), ex);
        } catch (Exception ex) {
            throw new TalonOneClientException("Unexpected error confirming loyalty", ex);
        }
    }

    /**
     * Exception class for TalonOneClient errors.
     */
    public static class TalonOneClientException extends RuntimeException {
        private final int statusCode;

        public TalonOneClientException(String message) {
            super(message);
            this.statusCode = -1;
        }

        public TalonOneClientException(String message, int statusCode) {
            super(message);
            this.statusCode = statusCode;
        }

        public TalonOneClientException(String message, Throwable cause) {
            super(message, cause);
            this.statusCode = -1;
        }

        public TalonOneClientException(String message, int statusCode, Throwable cause) {
            super(message, cause);
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }

    /**
     * Internal DTO for loyalty confirmation requests.
     * Adjust the field name if Talon.One expects a different structure.
     */
    private static class LoyaltyConfirmRequest {
        private final double totalAmount;

        public LoyaltyConfirmRequest(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public double getTotalAmount() {
            return totalAmount;
        }
    }
}
