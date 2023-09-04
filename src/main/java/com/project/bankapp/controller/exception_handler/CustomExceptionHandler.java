package com.project.bankapp.controller.exception_handler;

import com.project.bankapp.exception.DataNotFoundException;
import com.project.bankapp.exception.InsufficientFundsException;
import com.project.bankapp.exception.TransactionNotAllowedException;
import com.project.bankapp.exception.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> handelDataNotFoundException() {
        log.error("Data not found");
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> handelInsufficientFundsException() {
        log.error("Insufficient funds");
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(TransactionNotAllowedException.class)
    public ResponseEntity<String> handelTransactionNotAllowedException() {
        log.error("Transaction not allowed");
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handelUserAlreadyExistsException() {
        log.error("User already exists");
        return ResponseEntity.badRequest().build();
    }
}
