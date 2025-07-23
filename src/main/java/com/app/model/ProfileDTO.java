package com.app.model;

import lombok.*;

/**
 * DTO for user profile updates (for Talon.One profile integration).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDTO {

    private Long userId;

    private int totalOrders;

    private double totalSpent;
}
