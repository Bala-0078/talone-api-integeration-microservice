package com.app.model;

import lombok.*;
import javax.validation.constraints.NotNull;

/**
 * DTO for placing an order.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @NotNull
    private Long userId;

    @NotNull
    private CartRequest cartRequest;
}
