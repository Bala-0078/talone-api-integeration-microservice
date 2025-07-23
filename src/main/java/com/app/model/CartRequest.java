package com.app.model;

import lombok.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * DTO representing a cart to be evaluated for rewards.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartRequest {

    @NotNull
    private Long userId;

    @NotNull
    private List<ItemDTO> items;

    @Positive
    private double total;

    @NotNull
    private ProfileDTO profileDTO; // For Talon.One profile update

    // Converts CartRequest to SessionDTO for Talon.One session evaluation
    public SessionDTO toSessionDTO(String sessionId) {
        return SessionDTO.builder()
                .sessionId(sessionId)
                .userId(this.userId)
                .items(this.items)
                .total(this.total)
                .profileDTO(this.profileDTO)
                .build();
    }
}
