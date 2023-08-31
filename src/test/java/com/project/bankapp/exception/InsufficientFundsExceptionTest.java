package com.project.bankapp.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InsufficientFundsExceptionTest {
    @Test
    void getMessage_shouldReturnProvidedMessage() {
        // given
        String message = "Insufficient funds";
        InsufficientFundsException exception = new InsufficientFundsException(message);
        // when
        String actualMessage = exception.getMessage();
        // then
        assertEquals(message, actualMessage);
    }
}