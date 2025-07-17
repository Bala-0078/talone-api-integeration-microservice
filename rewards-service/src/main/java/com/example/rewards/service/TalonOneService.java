package com.example.rewards.service;

import com.example.rewards.config.TalonOneConfig;
import com.example.rewards.exception.TalonOneException;
import com.example.rewards.model.RewardResponse;
import com.example.rewards.model.TalonOneSessionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class TalonOneService {
    private static final Logger logger = LoggerFactory.getLogger(TalonOneService.class);

    private final TalonOneConfig talonOneConfig;
    private final RestTemplate restTemplate;

    @Autowired
    public TalonOneService(TalonOneConfig talonOneConfig, RestTemplate restTemplate) {
        this.talonOneConfig = talonOneConfig;
        this.restTemplate = restTemplate;
    }

    public RewardResponse evaluateSession(TalonOneSessionRequest request) {
        String url = talonOneConfig.getApiUrl() + "/v1/customer_sessions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "ApiKey-v1 " + talonOneConfig.getApiKey());
        headers.set("Application-Id", talonOneConfig.getApplicationId());
        HttpEntity<TalonOneSessionRequest> entity = new HttpEntity<>(request, headers);
        try {
            ResponseEntity<RewardResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    RewardResponse.class
            );
            logger.info("Talon.One session evaluated successfully for customerId={}", request.getCustomerId());
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            logger.error("Talon.One API error: status={}, body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new TalonOneException("Talon.One API error: " + ex.getResponseBodyAsString(), ex);
        } catch (Exception ex) {
            logger.error("Talon.One integration error", ex);
            throw new TalonOneException("Talon.One integration error", ex);
        }
    }
}
