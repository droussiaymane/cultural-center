package com.ju.islamicculturalcenter.exceptions;

public class CustomBadCredentialsException extends RuntimeException {

    public CustomBadCredentialsException() {
    }

    public CustomBadCredentialsException(String message) {
        super(message);
    }

    public CustomBadCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}
