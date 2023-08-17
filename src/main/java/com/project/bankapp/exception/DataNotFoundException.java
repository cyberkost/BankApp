package com.project.bankapp.exception;

/**
 * Custom exception class for indicating that requested data was not found.
 * This exception is typically thrown when attempting to retrieve or access data that does not exist.
 */
public class DataNotFoundException extends RuntimeException {
    /**
     * Constructs a new DataNotFoundException with the specified error message.
     *
     * @param message The error message describing the cause of the exception.
     */
    public DataNotFoundException(String message) {
        super(message);
    }
}
