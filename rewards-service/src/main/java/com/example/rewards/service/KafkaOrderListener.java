package com.example.rewards.service;

import com.example.rewards.model.OrderEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class KafkaOrderListener {
    private static final Logger logger = LoggerFactory.getLogger(KafkaOrderListener.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    // Simple in-memory idempotency store; replace with Redis/DB in production
    private final Set<String> processedOrderIds = new HashSet<>();

    @KafkaListener(topics = "order-events", groupId = "rewards-service-group")
    public void listen(ConsumerRecord<String, String> record) {
        try {
            OrderEvent orderEvent = objectMapper.readValue(record.value(), OrderEvent.class);
            if (orderEvent.getOrderId() == null) {
                logger.warn("Received order event with null orderId: {}", record.value());
                return;
            }
            if (processedOrderIds.contains(orderEvent.getOrderId())) {
                logger.info("Duplicate order event received for orderId={}, skipping.", orderEvent.getOrderId());
                return;
            }
            processedOrderIds.add(orderEvent.getOrderId());
            // Confirm loyalty actions (stubbed)
            logger.info("Processing order event: orderId={}, customerId={}, totalAmount={}",
                    orderEvent.getOrderId(), orderEvent.getCustomerId(), orderEvent.getTotalAmount());
            // TODO: Integrate with Talon.One for loyalty confirmation if required
        } catch (Exception ex) {
            logger.error("Failed to process order event from Kafka: {}", record.value(), ex);
        }
    }
}
