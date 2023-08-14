package com.project.bankapp.registration;

import com.project.bankapp.dto.ClientRegistrationDto;

public interface ClientRegistrationService {
    void registerNewClient(ClientRegistrationDto clientRegistrationDto);
}
