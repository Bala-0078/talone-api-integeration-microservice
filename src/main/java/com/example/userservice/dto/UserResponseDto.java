package com.example.userservice.dto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO for user response data.
 */
@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Integer totalOrders;
    private Double totalSpent;
}
