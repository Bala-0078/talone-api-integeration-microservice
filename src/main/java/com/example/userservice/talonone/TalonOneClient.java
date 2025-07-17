package com.example.userservice.talonone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class TalonOneClient {
    @Value("${talonone.api.url}")
    private String talonOneApiUrl;

    @Value("${talonone.api.key}")
    private String talonOneApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Registers a user in the Talon.One Campaign Rules Engine.
     * @param userId The user ID
     * @param email The user email
     * @param name The user name
     * @return true if successful, false otherwise
     */
    public boolean registerUserInTalonOne(Long userId, String email, String name) {
        String url = talonOneApiUrl + "/v1/customers";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "ApiKey " + talonOneApiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("integrationId", userId.toString());
        body.put("attributes", Map.of("email", email, "name", name));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            // Log error in real implementation
            return false;
        }
    }
}
