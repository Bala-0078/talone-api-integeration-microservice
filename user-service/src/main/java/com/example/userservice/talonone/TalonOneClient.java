package com.example.userservice.talonone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Client for interacting with Talon.One API.
 */
@Component
public class TalonOneClient {

    @Value("${talonone.api.url}")
    private String talonOneApiUrl;

    @Value("${talonone.api.key}")
    private String talonOneApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Registers a user in Talon.One.
     */
    public void registerUserInTalonOne(String userId, String email) {
        String url = talonOneApiUrl + "/v1/customers";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "ApiKey-v1 " + talonOneApiKey);

        Map<String, Object> body = Map.of(
                "integrationId", userId,
                "attributes", Map.of("email", email)
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to register user in Talon.One: " + response.getBody());
        }
    }
}
