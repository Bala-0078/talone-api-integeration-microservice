package com.example.rewards.service;

import com.example.rewards.model.OrderEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaOrderListener {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "orders", groupId = "rewards-service-group")
    public void listenOrderEvents(String message) {
        try {
            OrderEvent event = objectMapper.readValue(message, OrderEvent.class);
            log.info("Received order event: {}", event);
            // TODO: Confirm loyalty actions based on event.loyaltyActions
            // This could involve updating user profiles, calling Talon.One, etc.
        } catch (Exception e) {
            log.error("Failed to process order event: {}", e.getMessage());
        }
    }
}
