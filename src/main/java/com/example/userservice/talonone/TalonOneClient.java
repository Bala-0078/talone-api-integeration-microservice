package com.example.userservice.talonone;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TalonOneClient {
    @Value("${talonone.apiKey}")
    private String apiKey;

    @Value("${talonone.baseUrl}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Registers a user in Talon.One
     * @param userId user id
     * @param email user email
     * @return true if registration is successful, false otherwise
     */
    public boolean registerUser(Long userId, String email) {
        String url = baseUrl + "/customers";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "ApiKey " + apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("integrationId", userId.toString());
        body.put("email", email);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException e) {
            log.error("Talon.One registration failed: {}", e.getResponseBodyAsString());
            return false;
        } catch (Exception e) {
            log.error("Talon.One registration error: {}", e.getMessage());
            return false;
        }
    }
}
