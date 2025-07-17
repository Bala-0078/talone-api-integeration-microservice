package com.example.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequestDto {
    @NotNull
    @Schema(description = "User ID placing the order", example = "1")
    private Long userId;

    @NotEmpty
    @Schema(description = "List of cart items")
    private List<CartItemDto> cartItems;

    @NotNull
    @Schema(description = "Total amount before discount", example = "100.00")
    private BigDecimal totalAmount;
}
