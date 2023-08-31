package com.project.bankapp.controller;

import com.project.bankapp.dto.ClientRegistrationDto;
import com.project.bankapp.dto.ManagerRegistrationDto;
import com.project.bankapp.registration.ClientRegistrationService;
import com.project.bankapp.registration.ManagerRegistrationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationControllerTest {
    @Mock
    ClientRegistrationService clientRegistrationService;
    @Mock
    ManagerRegistrationService managerRegistrationService;
    @InjectMocks
    RegistrationController registrationController;
    ClientRegistrationDto clientRegistrationDto = ClientRegistrationDto.builder().build();
    ManagerRegistrationDto managerRegistrationDto = ManagerRegistrationDto.builder().build();

    @Test
    void registerClient_success() {
        // when
        ResponseEntity<ClientRegistrationDto> actual = registrationController.registerClient(clientRegistrationDto);
        // then
        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertEquals(clientRegistrationDto, actual.getBody());
        verify(clientRegistrationService).registerNewClient(clientRegistrationDto);
    }

    @Test
    void registerManager_success() {
        // when
        ResponseEntity<ManagerRegistrationDto> response = registrationController.registerManager(managerRegistrationDto);
        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(managerRegistrationDto, response.getBody());
        verify(managerRegistrationService).registerNewManager(managerRegistrationDto);
    }
}