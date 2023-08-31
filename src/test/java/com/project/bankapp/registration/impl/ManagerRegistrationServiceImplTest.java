package com.project.bankapp.registration.impl;

import com.project.bankapp.dto.ManagerRegistrationDto;
import com.project.bankapp.entity.Manager;
import com.project.bankapp.exception.UserAlreadyExistsException;
import com.project.bankapp.service.ManagerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManagerRegistrationServiceImplTest {
    @Mock
    JdbcUserDetailsManager userDetailsManager;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    ManagerService managerService;
    @InjectMocks
    ManagerRegistrationServiceImpl managerRegistrationService;
    ManagerRegistrationDto managerRegistrationDto = ManagerRegistrationDto.builder().build();

    @Test
    void registerNewManager_success() {
        // given
        managerRegistrationDto.setLogin("username");
        managerRegistrationDto.setPassword("password");
        managerRegistrationDto.setFirstName("John");
        managerRegistrationDto.setLastName("Doe");
        managerRegistrationDto.setDescription("Manager description");

        when(passwordEncoder.encode(managerRegistrationDto.getPassword())).thenReturn("encodedPassword");
        when(userDetailsManager.userExists(managerRegistrationDto.getLogin())).thenReturn(false);
        // when
        managerRegistrationService.registerNewManager(managerRegistrationDto);
        // then
        verify(passwordEncoder).encode(managerRegistrationDto.getPassword());
        verify(userDetailsManager).userExists(managerRegistrationDto.getLogin());
        verify(userDetailsManager).createUser(any());
        verify(managerService).save(any(Manager.class));
    }
}