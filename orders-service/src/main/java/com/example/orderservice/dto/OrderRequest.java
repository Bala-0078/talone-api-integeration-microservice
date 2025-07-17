package com.example.orderservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class OrderRequest {
    @NotNull
    private UUID userId;

    @NotEmpty
    private List<CartItemDto> cartItems;

    @NotNull
    private BigDecimal totalAmount;
}
