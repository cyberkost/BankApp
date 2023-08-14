package com.project.bankapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LogoutController {
    @PostMapping("logout")
    public void logout() {
        log.info("endpoint request: logout");
        // Fake REST Controller exposing a post-request mapping for the /logout path.
        // This will make logout available in Swagger-UI.
    }
}
