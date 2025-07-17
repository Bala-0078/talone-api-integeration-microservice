package com.example.userservice.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * DTO for user update request.
 */
public class UserUpdateRequest {
    @Size(min = 3, max = 50)
    private String username;

    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 6, max = 100)
    private String password;

    // Getters and setters
}
