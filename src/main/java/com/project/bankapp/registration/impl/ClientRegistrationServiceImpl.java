package com.project.bankapp.registration.impl;

import com.project.bankapp.dto.ClientRegistrationDto;
import com.project.bankapp.entity.Client;
import com.project.bankapp.entity.Manager;
import com.project.bankapp.entity.enums.ClientStatus;
import com.project.bankapp.entity.enums.ManagerStatus;
import com.project.bankapp.entity.enums.Roles;
import com.project.bankapp.exception.UserAlreadyExistsException;
import com.project.bankapp.registration.ClientRegistrationService;
import com.project.bankapp.service.ClientService;
import com.project.bankapp.service.ManagerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientRegistrationServiceImpl implements ClientRegistrationService {
    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final ClientService clientService;
    private final ManagerService managerService;

    private void saveClientCredentials(String email, String encodedPassword) {
        userDetailsManager.createUser(User.withUsername(email)
                .password(encodedPassword)
                .roles(Roles.USER.name())
                .build());
    }

    private Client initializeNewClientInstance(ClientRegistrationDto registration) {
        if (registration == null) {
            throw new IllegalArgumentException("registrationDto cannot be null");
        }
        return Client.builder()
                .status(ClientStatus.ACTIVE)
                .firstName(registration.getFirstName())
                .lastName(registration.getLastName())
                .email(registration.getEmail())
                .address(registration.getAddress())
                .phone(registration.getPhone())
                .build();
    }

    @Override
    @Transactional
    public void registerNewClient(ClientRegistrationDto clientRegistrationDto) {
        String email = clientRegistrationDto.getEmail();
        String encodedPassword = passwordEncoder.encode(clientRegistrationDto.getPassword());
        if (userDetailsManager.userExists(email)) {
            throw new UserAlreadyExistsException("Unable to register username, already exists in DB");
        }
        saveClientCredentials(email, encodedPassword);
        List<Manager> activeManagers = managerService.findManagersSortedByClientQuantityWhereManagerStatusIs(ManagerStatus.ACTIVE);
        Manager firstManager = managerService.getFirstManager(activeManagers);
        Client client = initializeNewClientInstance(clientRegistrationDto);
        client.setManagerUuid(firstManager.getUuid());
        clientService.save(client);
        log.info("client created");
    }
}
