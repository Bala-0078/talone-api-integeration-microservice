package com.example.rewards;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RewardsServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void evaluateCartEndpointReturnsOk() throws Exception {
        String payload = "{\"userId\":\"12345\",\"cartItems\":[{\"itemId\":\"A1\",\"quantity\":2,\"price\":50}]}";
        mockMvc.perform(post("/rewards/evaluate")
                .contentType("application/json")
                .content(payload))
                .andExpect(status().isOk());
    }
}
