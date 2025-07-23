package com.app.service;

import com.app.model.User;
import com.app.model.ProfileDTO;
import com.app.repository.UserRepository;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

/**
 * Service for user-related business logic.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Fetch user details by ID.
     * @param id User ID
     * @return User entity
     * @throws EntityNotFoundException if user not found
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    /**
     * Update user's totalOrders and totalSpent statistics.
     * @param id User ID
     * @param profileDTO DTO containing updated stats
     */
    public void updateUserStats(Long id, ProfileDTO profileDTO) {
        User user = getUserById(id);
        user.setTotalOrders(profileDTO.getTotalOrders());
        user.setTotalSpent(profileDTO.getTotalSpent());
        userRepository.save(user);
    }
}
