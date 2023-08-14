package com.project.bankapp.controller;

import com.project.bankapp.dto.ClientRegistrationDto;
import com.project.bankapp.dto.ManagerRegistrationDto;
import com.project.bankapp.registration.ClientRegistrationService;
import com.project.bankapp.registration.ManagerRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling client and manager registration operations.
 */
@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {
    private final ClientRegistrationService clientRegistrationService;
    private final ManagerRegistrationService managerRegistrationService;

    /**
     * Registers a new client.
     *
     * @param clientRegistrationDto The DTO containing client registration information.
     * @return A ResponseEntity with the registered ClientRegistrationDto.
     */
    @PostMapping(value = "/new-client")
    public ResponseEntity<ClientRegistrationDto> registerClient(@RequestBody ClientRegistrationDto clientRegistrationDto) {
        log.info("endpoint request: new client registration");
        clientRegistrationService.registerNewClient(clientRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientRegistrationDto);
    }

    /**
     * Registers a new manager.
     *
     * @param managerRegistrationDto The DTO containing manager registration information.
     * @return A ResponseEntity with the registered ManagerRegistrationDto.
     */
    @PostMapping(value = "/new-manager")
    public ResponseEntity<ManagerRegistrationDto> registerManager(@RequestBody ManagerRegistrationDto managerRegistrationDto) {
        log.info("endpoint request: new manager registration");
        managerRegistrationService.registerNewManager(managerRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(managerRegistrationDto);
    }
}
