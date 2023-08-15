package com.project.bankapp.exception;

/**
 * Custom exception class representing an exception when a transaction is not allowed or permitted.
 * This exception can be thrown to indicate that a specific transaction operation is not allowed or authorized.
 */
public class TransactionNotAllowedException extends RuntimeException {
    /**
     * Constructs a new instance of the exception with the specified error message.
     *
     * @param message The error message explaining why the transaction is not allowed.
     */
    public TransactionNotAllowedException(String message) {
        super(message);
    }
}
