package com.example.orderservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequest {
    @NotNull
    private Long userId;

    @NotEmpty
    private List<OrderItemDto> items;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal totalAmount;
}

@Data
class OrderItemDto {
    @NotNull
    private Long productId;

    @NotNull
    @DecimalMin("1")
    private Integer quantity;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;
}
