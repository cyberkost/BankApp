package com.project.bankapp.controller;

import com.project.bankapp.dto.ClientRegistrationDto;
import com.project.bankapp.registration.ClientRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {
    private final ClientRegistrationService clientRegistrationService;

    @PostMapping(value = "/new-client")
    public ResponseEntity<ClientRegistrationDto> registerClient(@RequestBody ClientRegistrationDto clientRegistrationDto) {
        log.info("endpoint request: new client registration");
        clientRegistrationService.registerNewClient(clientRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientRegistrationDto);
    }
}
