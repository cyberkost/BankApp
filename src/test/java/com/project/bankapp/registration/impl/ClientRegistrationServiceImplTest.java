package com.project.bankapp.registration.impl;

import com.project.bankapp.dto.ClientRegistrationDto;
import com.project.bankapp.entity.Client;
import com.project.bankapp.entity.Manager;
import com.project.bankapp.entity.enums.ManagerStatus;
import com.project.bankapp.exception.UserAlreadyExistsException;
import com.project.bankapp.service.ClientService;
import com.project.bankapp.service.ManagerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientRegistrationServiceImplTest {
    @Mock
    JdbcUserDetailsManager userDetailsManager;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    ClientService clientService;
    @Mock
    ManagerService managerService;
    @InjectMocks
    ClientRegistrationServiceImpl clientRegistrationService;
    @Captor
    ArgumentCaptor<Client> clientCaptor;
    ClientRegistrationDto clientRegistrationDto = ClientRegistrationDto.builder().build();

    @Test
    void registerNewClient_success() {
        // given
        clientRegistrationDto.setFirstName("John");
        clientRegistrationDto.setLastName("Doe");
        clientRegistrationDto.setEmail("john.doe@example.com");
        clientRegistrationDto.setAddress("123 Main St");
        clientRegistrationDto.setPhone("555-1234");
        clientRegistrationDto.setPassword("password");

        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(clientRegistrationDto.getPassword())).thenReturn(encodedPassword);
        when(userDetailsManager.userExists(clientRegistrationDto.getEmail())).thenReturn(false);

        List<Manager> activeManagers = List.of(new Manager());
        when(managerService.findManagersSortedByClientQuantityWhereManagerStatusIs(ManagerStatus.ACTIVE)).thenReturn(activeManagers);

        Manager firstManager = new Manager();
        when(managerService.getFirstManager(activeManagers)).thenReturn(firstManager);
        // when
        clientRegistrationService.registerNewClient(clientRegistrationDto);
        // then
        verify(userDetailsManager).createUser(any());
        verify(clientService).save(clientCaptor.capture());

        Client savedClient = clientCaptor.getValue();
        assertEquals(clientRegistrationDto.getFirstName(), savedClient.getFirstName());
        assertEquals(clientRegistrationDto.getLastName(), savedClient.getLastName());
        assertEquals(clientRegistrationDto.getEmail(), savedClient.getEmail());
        assertEquals(clientRegistrationDto.getAddress(), savedClient.getAddress());
        assertEquals(clientRegistrationDto.getPhone(), savedClient.getPhone());
        assertEquals(firstManager.getUuid(), savedClient.getManagerUuid());
    }

    @Test
    void registerNewClient_existingUser_throwsUserAlreadyExistsException() {
        // given
        when(userDetailsManager.userExists(clientRegistrationDto.getEmail())).thenReturn(true);
        // when, then
        assertThrows(UserAlreadyExistsException.class, () -> clientRegistrationService.registerNewClient(clientRegistrationDto));
        verify(userDetailsManager, never()).createUser(any());
        verify(clientService, never()).save(any());
    }
}
