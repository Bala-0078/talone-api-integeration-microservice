package com.example.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    @NotNull
    @Schema(description = "Product ID", example = "101")
    private Long productId;

    @NotNull
    @Min(1)
    @Schema(description = "Quantity of the product", example = "2")
    private Integer quantity;

    @NotNull
    @Schema(description = "Unit price of the product", example = "25.00")
    private BigDecimal price;
}
