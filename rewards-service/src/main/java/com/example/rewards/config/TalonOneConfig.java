package com.example.rewards.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class TalonOneConfig {
    @Value("${talonone.base-url}")
    private String talonOneBaseUrl;

    @Bean
    public WebClient talonOneWebClient() {
        return WebClient.builder()
                .baseUrl(talonOneBaseUrl)
                .build();
    }
}
