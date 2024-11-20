package com.example.Library_Management_System.exception;

public class DuplicateBorrowerEmailException extends RuntimeException {
    public DuplicateBorrowerEmailException(String message) {
        super(message);
    }
}
