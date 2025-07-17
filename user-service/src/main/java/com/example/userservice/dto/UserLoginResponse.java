package com.example.userservice.dto;

/**
 * DTO for user login response.
 */
public class UserLoginResponse {
    private String token;
    private String username;
    private String email;

    public UserLoginResponse(String token, String username, String email) {
        this.token = token;
        this.username = username;
        this.email = email;
    }

    // Getters and setters
}
