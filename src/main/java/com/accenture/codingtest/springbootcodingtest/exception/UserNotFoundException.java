package com.accenture.codingtest.springbootcodingtest.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}
