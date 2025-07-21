package com.app.controller;

import com.app.dto.UserDTO;
import com.app.dto.UserStatsUpdateRequest;
import com.app.exception.UserNotFoundException;
import com.app.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for managing users.
 * Handles fetching user details and updating user statistics.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Fetch user details by user ID.
     *
     * @param id the user ID
     * @return ResponseEntity containing UserDTO if found, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.findUserById(id);
        if (userDTO == null) {
            throw new UserNotFoundException("User with id " + id + " not found.");
        }
        return ResponseEntity.ok(userDTO);
    }

    /**
     * Update user's totalOrders and totalSpent.
     *
     * @param id the user ID
     * @param request the update request body
     * @return ResponseEntity containing updated UserDTO if successful, or 404 if user not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserStats(
            @PathVariable Long id,
            @Valid @RequestBody UserStatsUpdateRequest request
    ) {
        UserDTO updatedUser = userService.updateUserStats(id, request);
        if (updatedUser == null) {
            throw new UserNotFoundException("User with id " + id + " not found.");
        }
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Handle UserNotFoundException and return 404 Not Found.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Handle validation errors and return 400 Bad Request with field error details.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }
}
