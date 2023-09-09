package com.project.bankapp.service;

import com.project.bankapp.dto.ChangePasswordDto;

/**
 * The CredentialService interface defines methods for managing user credentials, specifically for changing passwords.
 * Implementations of this interface are responsible for handling password changes for users.
 */
public interface CredentialService {
    /**
     * Changes the password for a user with the specified username.
     *
     * @param currentUsername   The username of the user whose password needs to be changed.
     * @param changePasswordDto A Data Transfer Object (DTO) containing the necessary information for password change.
     *                          It typically includes the new password and any additional information required for
     *                          security validation.
     */
    void changePassword(String currentUsername, ChangePasswordDto changePasswordDto);
}
