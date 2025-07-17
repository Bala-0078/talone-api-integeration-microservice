package com.example.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    @NotBlank
    private String productId;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    private BigDecimal price;
}
