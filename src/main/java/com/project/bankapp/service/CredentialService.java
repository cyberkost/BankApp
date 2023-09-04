package com.project.bankapp.service;

import com.project.bankapp.dto.ChangePasswordDto;

public interface CredentialService {
    void changePassword(String currentUsername, ChangePasswordDto changePasswordDto);
}
