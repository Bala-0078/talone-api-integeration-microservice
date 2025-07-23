package com.app.controller;

import com.app.model.User;
import com.app.model.ProfileDTO;
import com.app.service.UserService;
lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * Controller for user-related operations.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    /**
     * Fetch user details by ID.
     * @param id User ID
     * @return User details
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Update user's totalOrders and totalSpent.
     * @param id User ID
     * @param profileDTO DTO containing stats to update
     * @return No content
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserStats(
            @PathVariable Long id,
            @RequestBody @Valid ProfileDTO profileDTO) {
        userService.updateUserStats(id, profileDTO);
        return ResponseEntity.noContent().build();
    }
}
