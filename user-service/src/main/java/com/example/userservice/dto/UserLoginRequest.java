package com.example.userservice.dto;

import javax.validation.constraints.NotBlank;

/**
 * DTO for user login request.
 */
public class UserLoginRequest {
    @NotBlank(message = "Username or email is required")
    private String usernameOrEmail;

    @NotBlank(message = "Password is required")
    private String password;

    // Getters and setters
}
