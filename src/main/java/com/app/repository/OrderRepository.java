package com.app.repository;

import com.app.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Order entity.
 * Provides CRUD operations and query methods for Order data.
 * 
 * This interface extends JpaRepository, enabling standard data access methods such as
 * save, findById, findAll, deleteById, etc., for Order entities.
 * 
 * Note: No custom query methods are defined, as default JpaRepository methods are sufficient.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Default query methods provided by JpaRepository are sufficient.
}
