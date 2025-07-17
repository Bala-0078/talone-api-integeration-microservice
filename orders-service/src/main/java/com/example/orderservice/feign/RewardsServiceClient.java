package com.example.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "rewards-service", url = "${rewards-service.url}")
public interface RewardsServiceClient {
    @PostMapping("/rewards/discount")
    Map<String, Object> calculateDiscount(@RequestBody Map<String, Object> orderInfo);
}
