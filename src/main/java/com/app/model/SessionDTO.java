package com.app.model;

import lombok.*;
import java.util.List;

/**
 * DTO for Talon.One session evaluation (maps to IntegrationRequest).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionDTO {

    private String sessionId; // Typically a unique cart/session identifier

    private Long userId;

    private List<ItemDTO> items;

    private double total;

    private ProfileDTO profileDTO;
}
