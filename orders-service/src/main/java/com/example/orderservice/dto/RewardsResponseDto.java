package com.example.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RewardsResponseDto {
    private BigDecimal discount;
    private String campaignName;
    // Add other fields as needed
}
