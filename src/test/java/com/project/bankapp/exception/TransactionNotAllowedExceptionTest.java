package com.project.bankapp.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionNotAllowedExceptionTest {
    @Test
    void getMessage_shouldReturnProvidedMessage() {
        // given
        String message = "Transaction not allowed";
        TransactionNotAllowedException exception = new TransactionNotAllowedException(message);
        // when
        String actualMessage = exception.getMessage();
        // then
        assertEquals(message, actualMessage);
    }

    @Test
    void getMessage_shouldReturnNullWhenNoMessageProvided() {
        // given
        TransactionNotAllowedException exception = new TransactionNotAllowedException();
        // when
        String actualMessage = exception.getMessage();
        // then
        assertNull(actualMessage);
    }
}