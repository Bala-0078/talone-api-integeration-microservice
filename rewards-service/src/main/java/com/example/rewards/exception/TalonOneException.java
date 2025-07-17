package com.example.rewards.exception;

public class TalonOneException extends RuntimeException {
    public TalonOneException(String message) {
        super(message);
    }

    public TalonOneException(String message, Throwable cause) {
        super(message, cause);
    }
}
