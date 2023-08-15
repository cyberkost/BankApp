package com.project.bankapp.registration;

import com.project.bankapp.dto.ManagerRegistrationDto;

/**
 * Service interface for handling the registration of new managers in the system.
 */
public interface ManagerRegistrationService {
    void registerNewManager(ManagerRegistrationDto managerRegistrationDto);
}
