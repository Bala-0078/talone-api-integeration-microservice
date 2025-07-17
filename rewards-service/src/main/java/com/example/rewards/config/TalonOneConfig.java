package com.example.rewards.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class TalonOneConfig {

    @Value("${TalonOne.base-url}")
    private String talonOneBaseUrl;

    @Value("${TalonOne.api-key}")
    private String talonOneApiKey;

    @Bean
    public WebClient talonOneWebClient() {
        return WebClient.builder()
                .baseUrl(talonOneBaseUrl)
                .defaultHeader("Authorization", "ApiKey " + talonOneApiKey)
                .build();
    }
}
