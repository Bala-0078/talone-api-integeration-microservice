package com.app.model;

import lombok.*;
import javax.persistence.*;
import java.util.List;

/**
 * Entity representing a user in the system.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalOrders;

    private double totalSpent;

    // One user can have many orders
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    // Converts User entity to ProfileDTO for Talon.One profile update
    public ProfileDTO toProfileDTO() {
        return ProfileDTO.builder()
                .userId(this.id)
                .totalOrders(this.totalOrders)
                .totalSpent(this.totalSpent)
                .build();
    }
}
