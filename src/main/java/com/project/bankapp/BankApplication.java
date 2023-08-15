package com.project.bankapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class that initializes and runs the Bank Application.
 */
@SpringBootApplication
public class BankApplication {
    /**
     * The main method to start the Bank Application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }
}
