package com.project.bankapp.registration;

import com.project.bankapp.dto.ClientRegistrationDto;

/**
 * Interface for managing client registration operations.
 * Implementations of this interface provide methods to register new clients with the system.
 */
public interface ClientRegistrationService {
    void registerNewClient(ClientRegistrationDto clientRegistrationDto);
}
