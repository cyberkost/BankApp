package com.project.bankapp.exception;

/**
 * Custom exception indicating that a user with the provided credentials already exists in the system.
 * This exception is typically thrown when attempting to create a new user with credentials that are already in use.
 */
public class UserAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new UserAlreadyExistsException with the specified error message.
     *
     * @param message The error message indicating the reason for the exception.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
