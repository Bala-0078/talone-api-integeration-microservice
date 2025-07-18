package com.example.userservice.exception;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
