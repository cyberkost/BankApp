package com.project.bankapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling logout-related operations.
 */
@RestController
@Slf4j
public class LogoutController {
    /**
     * Logs out the currently authenticated user.
     * This method exposes a POST request mapping for the /logout path to make logout available in Swagger-UI.
     * Actual logout logic is handled by the underlying Spring Security framework.
     */
    @PostMapping("logout")
    public void logout() {
        log.info("endpoint request: logout");
        // Fake REST Controller exposing a post-request mapping for the /logout path.
        // This will make logout available in Swagger-UI.
    }
}
