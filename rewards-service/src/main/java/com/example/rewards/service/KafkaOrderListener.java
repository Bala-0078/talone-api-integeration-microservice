package com.example.rewards.service;

import com.example.rewards.model.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaOrderListener {
    private static final Logger logger = LoggerFactory.getLogger(KafkaOrderListener.class);

    @KafkaListener(topics = "order-events", groupId = "rewards-service-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenOrderEvents(OrderEvent event) {
        logger.info("Received order event from Kafka: {}", event);
        // TODO: Confirm loyalty actions, update user statistics, ensure idempotency
        // For demonstration, just log the event
    }
}
