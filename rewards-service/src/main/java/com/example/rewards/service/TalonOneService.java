package com.example.rewards.service;

import com.example.rewards.config.TalonOneConfig;
import com.example.rewards.exception.TalonOneException;
import com.example.rewards.model.TalonOneSessionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TalonOneService {
    @Autowired
    private TalonOneConfig talonOneConfig;

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> evaluateSession(TalonOneSessionRequest sessionRequest) {
        String url = talonOneConfig.getBaseUrl() + "/integration/v1/customer_sessions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "ApiKey-v1 " + talonOneConfig.getApiKey());
        HttpEntity<TalonOneSessionRequest> request = new HttpEntity<>(sessionRequest, headers);
        try {
            return restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (Exception e) {
            throw new TalonOneException("Failed to evaluate session with Talon.One: " + e.getMessage(), e);
        }
    }
}
