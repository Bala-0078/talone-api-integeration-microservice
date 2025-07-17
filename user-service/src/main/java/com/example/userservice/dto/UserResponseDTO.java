package com.example.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Integer totalOrders;
    private Double totalSpent;
}
