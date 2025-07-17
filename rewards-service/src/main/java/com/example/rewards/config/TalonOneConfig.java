package com.example.rewards.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for Talon.One API integration.
 */
@Configuration
@ConfigurationProperties(prefix = "talonone")
public class TalonOneConfig {
    /**
     * Talon.One API base URL.
     */
    private String baseUrl;
    /**
     * Talon.One API key.
     */
    private String apiKey;

    public String getBaseUrl() {
        return baseUrl;
    }
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    public String getApiKey() {
        return apiKey;
    }
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Bean
    public RestTemplate talonOneRestTemplate() {
        return new RestTemplate();
    }
}
