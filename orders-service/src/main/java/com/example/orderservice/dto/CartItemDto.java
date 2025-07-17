package com.example.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "Cart item for order placement")
public class CartItemDto {
    @NotNull
    @Schema(description = "Product ID", example = "1001")
    private Long productId;

    @NotNull
    @Min(1)
    @Schema(description = "Quantity", example = "2")
    private Integer quantity;

    @NotNull
    @Schema(description = "Price per item", example = "19.99")
    private BigDecimal price;

    // Getters and setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
