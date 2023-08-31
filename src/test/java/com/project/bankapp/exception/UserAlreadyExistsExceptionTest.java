package com.project.bankapp.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAlreadyExistsExceptionTest {
    @Test
    void constructor_withMessage_shouldSetMessage() {
        // given
        String message = "User already exists";
        // when
        UserAlreadyExistsException exception = new UserAlreadyExistsException(message);
        // then
        assertEquals(message, exception.getMessage());
    }

}