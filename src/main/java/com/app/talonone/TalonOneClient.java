package talonone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * TalonOneClient is a reusable and centralized client for interacting with Talon.One's Integration API.
 * It provides methods to update customer profiles, evaluate sessions for rewards, and confirm loyalty points.
 * <p>
 * Configuration:
 * - talonone.base-url: The base URL for Talon.One Integration API (e.g., https://yourbaseurl.talon.one)
 * - talonone.api-key: The API key for authenticating requests (should be prefixed with "ApiKey-v1 ")
 * <p>
 * Usage:
 * Inject this component where needed and call its methods to interact with Talon.One.
 */
@Component
public class TalonOneClient {

    @Value("${talonone.base-url}")
    private String baseUrl;

    @Value("${talonone.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public TalonOneClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Updates or creates a customer profile in Talon.One.
     *
     * @param userId The integration ID of the user (customer profile).
     * @param dto    The profile data to update (ProfileDTO should map to Talon.One's CustomerProfileIntegrationRequestV2).
     * @throws RestClientException if the request fails.
     */
    public void updateProfile(String userId, ProfileDTO dto) {
        String url = String.format("%s/v2/customer_profiles/%s", baseUrl, userId);
        HttpHeaders headers = createHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProfileDTO> entity = new HttpEntity<>(dto, headers);

        try {
            restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
        } catch (RestClientException ex) {
            // You may want to log or handle specific exceptions here
            throw ex;
        }
    }

    /**
     * Evaluates a session (cart) in Talon.One for rewards and discounts.
     *
     * @param dto The session data (SessionDTO should map to Talon.One's IntegrationRequest).
     * @return RewardsResponse containing discounts and applied rewards.
     * @throws RestClientException if the request fails.
     */
    public RewardsResponse evaluateSession(SessionDTO dto) {
        String url = String.format("%s/v2/customer_sessions/%s", baseUrl, dto.getSessionId());
        HttpHeaders headers = createHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Wrap dto in IntegrationRequest if needed, or send dto directly if compatible
        HttpEntity<SessionDTO> entity = new HttpEntity<>(dto, headers);

        try {
            ResponseEntity<RewardsResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    entity,
                    RewardsResponse.class
            );
            return response.getBody();
        } catch (RestClientException ex) {
            // You may want to log or handle specific exceptions here
            throw ex;
        }
    }

    /**
     * Confirms loyalty point usage for a user after order placement.
     *
     * @param userId      The integration ID of the user (customer profile).
     * @param totalAmount The total amount spent (should be wrapped as needed for Talon.One).
     * @throws RestClientException if the request fails.
     */
    public void confirmLoyalty(String userId, double totalAmount) {
        String url = String.format("%s/v1/loyalty/%s/confirm", baseUrl, userId);
        HttpHeaders headers = createHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // If Talon.One expects a JSON body with the amount, wrap it accordingly
        HttpEntity<Double> entity = new HttpEntity<>(totalAmount, headers);

        try {
            restTemplate.postForLocation(url, entity);
        } catch (RestClientException ex) {
            // You may want to log or handle specific exceptions here
            throw ex;
        }
    }

    /**
     * Creates HTTP headers with the Authorization header set for Talon.One.
     *
     * @return HttpHeaders with Authorization set.
     */
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "ApiKey-v1 " + apiKey);
        return headers;
    }
}
