package com.example.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.orderservice.dto.RewardsRequestDto;
import com.example.orderservice.dto.RewardsResponseDto;

@FeignClient(name = "rewards-service", url = "${rewards-service.url}")
public interface RewardsServiceClient {
    @PostMapping("/rewards/calculate-discount")
    RewardsResponseDto calculateDiscount(@RequestBody RewardsRequestDto request);
}
