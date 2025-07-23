package com.app.model;

import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * DTO representing an item in a cart or order.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {

    private Long id; // optional for DTO, used for updates

    @NotBlank
    private String sku;

    @NotBlank
    private String name;

    @Positive
    private int quantity;

    @Positive
    private double price;
}
