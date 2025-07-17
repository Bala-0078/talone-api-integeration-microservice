package com.example.rewards.service;

import com.example.rewards.config.TalonOneConfig;
import com.example.rewards.exception.TalonOneException;
import com.example.rewards.model.TalonOneSessionRequest;
import com.example.rewards.model.RewardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Service for interacting with Talon.One API.
 */
@Service
public class TalonOneService {
    private static final Logger logger = LoggerFactory.getLogger(TalonOneService.class);

    @Autowired
    private TalonOneConfig talonOneConfig;

    @Autowired
    private RestTemplate talonOneRestTemplate;

    /**
     * Evaluate cart/session with Talon.One API.
     * @param sessionRequest TalonOneSessionRequest
     * @return RewardResponse
     */
    public RewardResponse evaluateSession(TalonOneSessionRequest sessionRequest) {
        String url = talonOneConfig.getBaseUrl() + "/v1/customer_sessions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "ApiKey " + talonOneConfig.getApiKey());
        HttpEntity<TalonOneSessionRequest> entity = new HttpEntity<>(sessionRequest, headers);
        try {
            logger.info("Calling Talon.One API at {} for customerId {}", url, sessionRequest.getCustomerId());
            ResponseEntity<RewardResponse> response = talonOneRestTemplate.exchange(
                    url, HttpMethod.POST, entity, RewardResponse.class);
            logger.info("Talon.One API response: {}", response.getStatusCode());
            return response.getBody();
        } catch (RestClientException ex) {
            logger.error("Error calling Talon.One API: {}", ex.getMessage(), ex);
            throw new TalonOneException("Failed to evaluate session with Talon.One API", ex);
        }
    }
}
