package com.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

/**
 * Configuration class for RestTemplate used to interact with Talon.One's Integration API.
 * <p>
 * This configuration ensures:
 * <ul>
 *     <li>Singleton and thread-safe RestTemplate bean for TalonOneClient usage.</li>
 *     <li>Secure injection of the Talon.One API key from application properties.</li>
 *     <li>Request interceptor for concise logging and authentication header attachment.</li>
 * </ul>
 * <p>
 * The API key should be set in application properties as <b>talonone.api-key</b>.
 *
 * Example property:
 * <pre>
 * talonone.api-key=your-actual-api-key
 * </pre>
 */
@Configuration
public class RestTemplateConfig {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateConfig.class);

    @Value("${talonone.api-key}")
    private String talonOneApiKey;

    /**
     * Provides a singleton RestTemplate bean configured with a custom interceptor
     * for Talon.One Integration API.
     *
     * @return configured RestTemplate instance
     */
    @Bean
    public RestTemplate talonOneRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(talonOneInterceptor()));
        return restTemplate;
    }

    /**
     * Interceptor to add authentication header and log essential request details
     * for Talon.One API calls.
     *
     * @return ClientHttpRequestInterceptor instance
     */
    private ClientHttpRequestInterceptor talonOneInterceptor() {
        return new ClientHttpRequestInterceptor() {
            @Override
            public org.springframework.http.client.ClientHttpResponse intercept(
                    ClientHttpRequest request,
                    byte[] body,
                    ClientHttpRequestExecution execution
            ) throws IOException {
                // Attach Authorization header
                request.getHeaders().add("Authorization", "ApiKey-v1 " + talonOneApiKey);

                // Log HTTP method and URI (do NOT log headers or body to avoid sensitive data exposure)
                logger.info("Talon.One API Request: {} {}", request.getMethod(), request.getURI());

                return execution.execute(request, body);
            }
        };
    }
}
