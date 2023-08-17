package com.project.bankapp.exception;

/**
 * Custom exception class indicating that there are insufficient funds for a specific operation.
 * This exception is thrown when an operation requiring sufficient funds cannot be completed due to a lack of funds.
 */
public class InsufficientFundsException extends RuntimeException {
    /**
     * Constructs a new InsufficientFundsException with the specified error message.
     *
     * @param message The detail message indicating the reason for the exception.
     */
    public InsufficientFundsException(String message) {
        super(message);
    }
}
