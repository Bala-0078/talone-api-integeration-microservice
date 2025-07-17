package com.example.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponseDto {
    @Schema(description = "Order ID", example = "1001")
    private Long id;

    @Schema(description = "User ID", example = "1")
    private Long userId;

    @Schema(description = "List of cart items")
    private List<CartItemDto> cartItems;

    @Schema(description = "Total amount before discount", example = "100.00")
    private BigDecimal totalAmount;

    @Schema(description = "Discount applied", example = "10.00")
    private BigDecimal discount;

    @Schema(description = "Order creation timestamp")
    private LocalDateTime createdAt;
}
