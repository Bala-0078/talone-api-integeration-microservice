package com.example.userservice.exception;

/**
 * Exception thrown when a user already exists.
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
