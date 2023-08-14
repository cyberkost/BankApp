package com.project.bankapp.registration.impl;

import com.project.bankapp.dto.ManagerRegistrationDto;
import com.project.bankapp.entity.Manager;
import com.project.bankapp.entity.enums.ManagerStatus;
import com.project.bankapp.entity.enums.Roles;
import com.project.bankapp.exception.UserAlreadyExistsException;
import com.project.bankapp.registration.ManagerRegistrationService;
import com.project.bankapp.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerRegistrationServiceImpl implements ManagerRegistrationService {
    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final ManagerService managerService;

    private void saveManagerCredentials(String login, String encodedPassword) {
        userDetailsManager.createUser(User.withUsername(login)
                .password(encodedPassword)
                .roles(Roles.MANAGER.name(), Roles.USER.name())
                .build());
    }

    private Manager initializeNewManagerInstance(ManagerRegistrationDto managerRegistrationDto) {
        if (managerRegistrationDto == null) {
            throw new IllegalArgumentException("managerRegistrationDto cannot be null");
        }
        return Manager.builder()
                .firstName(managerRegistrationDto.getFirstName())
                .lastName(managerRegistrationDto.getLastName())
                .status(ManagerStatus.ACTIVE)
                .description(managerRegistrationDto.getDescription())
                .build();
    }

    @Override
    public void registerNewManager(ManagerRegistrationDto managerRegistrationDto) {
        String login = managerRegistrationDto.getLogin();
        String encodedPassword = passwordEncoder.encode(managerRegistrationDto.getPassword());
        if (userDetailsManager.userExists(login)) {
            throw new UserAlreadyExistsException("Unable to register username, already exists in DB");
        }
        saveManagerCredentials(login, encodedPassword);
        Manager manager = initializeNewManagerInstance(managerRegistrationDto);
        managerService.save(manager);
        log.info("manager created");
    }
}
