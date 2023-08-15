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

/**
 * Service implementation class responsible for handling manager registration operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerRegistrationServiceImpl implements ManagerRegistrationService {
    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final ManagerService managerService;

    /**
     * Saves manager credentials to the user details manager for authentication.
     *
     * @param login           The login or username associated with the manager.
     * @param encodedPassword The encoded password to be stored.
     */
    private void saveManagerCredentials(String login, String encodedPassword) {
        userDetailsManager.createUser(User.withUsername(login)
                .password(encodedPassword)
                .roles(Roles.MANAGER.name(), Roles.USER.name())
                .build());
    }

    /**
     * Initializes a new Manager instance based on the provided registration DTO.
     *
     * @param managerRegistrationDto The DTO containing manager registration information.
     * @return A newly constructed Manager instance.
     * @throws IllegalArgumentException if the manager registration DTO is null.
     */
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

    /**
     * Registers a new manager.
     *
     * @param managerRegistrationDto The DTO containing manager registration information.
     * @throws UserAlreadyExistsException if a user with the provided login already exists.
     */
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