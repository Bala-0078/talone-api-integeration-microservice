package com.example.userservice.talonone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Client for interacting with the Talon.One API during user registration.
 */
@Slf4j
@Component
public class TalonOneClient {
    @Value("${talonone.api.url}")
    private String talonOneApiUrl;

    @Value("${talonone.api.key}")
    private String talonOneApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Registers a user in Talon.One.
     * @param userId The user ID.
     * @param email The user email.
     * @return true if registration is successful, false otherwise.
     */
    public boolean registerUserInTalonOne(Long userId, String email) {
        String url = talonOneApiUrl + "/v1/customers";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "ApiKey " + talonOneApiKey);

        Map<String, Object> payload = new HashMap<>();
        payload.put("integrationId", userId.toString());
        payload.put("email", email);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (RestClientException e) {
            log.error("Failed to register user in Talon.One: {}", e.getMessage());
            return false;
        }
    }
}
