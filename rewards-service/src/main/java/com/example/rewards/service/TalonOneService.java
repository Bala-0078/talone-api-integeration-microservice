package com.example.rewards.service;

import com.example.rewards.exception.TalonOneException;
import com.example.rewards.model.TalonOneSessionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class TalonOneService {
    private final WebClient talonOneWebClient;

    @Value("${talonone.api-key}")
    private String talonOneApiKey;

    public Map<String, Object> evaluateSession(TalonOneSessionRequest sessionRequest) {
        try {
            return talonOneWebClient.post()
                    .uri("/v1/customer_sessions")
                    .header(HttpHeaders.AUTHORIZATION, "ApiKey " + talonOneApiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(sessionRequest)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Talon.One API error: {}", e.getResponseBodyAsString());
            throw new TalonOneException("Talon.One API error: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            log.error("Talon.One API exception: {}", e.getMessage());
            throw new TalonOneException("Talon.One API exception: " + e.getMessage(), e);
        }
    }
}
