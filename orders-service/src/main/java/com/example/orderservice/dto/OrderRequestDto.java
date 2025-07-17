package com.example.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Order placement request")
public class OrderRequestDto {
    @NotNull
    @Schema(description = "User ID", example = "42")
    private Long userId;

    @NotEmpty
    @Schema(description = "List of cart items")
    private List<CartItemDto> cartItems;

    // Getters and setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public List<CartItemDto> getCartItems() { return cartItems; }
    public void setCartItems(List<CartItemDto> cartItems) { this.cartItems = cartItems; }
}
