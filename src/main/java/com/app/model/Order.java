package com.app.model;

import lombok.*;
import javax.persistence.*;
import java.util.List;

/**
 * Entity representing an order placed by a user.
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many orders belong to one user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Store the userId for DTO mapping convenience
    private Long userId;

    // One order has many items
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> items;

    private double total;

    private double discount;

    // JSON string or serialized structure of applied rewards (for auditing)
    @ElementCollection
    @CollectionTable(name = "order_rewards", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "reward")
    private List<String> rewardsApplied;
}
