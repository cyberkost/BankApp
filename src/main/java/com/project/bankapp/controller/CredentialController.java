package com.project.bankapp.controller;

import com.project.bankapp.dto.ChangePasswordDto;
import com.project.bankapp.service.CredentialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * The {@code CredentialController} class is responsible for handling HTTP requests related to user credentials,
 * particularly for changing the user's password. It is a Spring Framework REST controller mapped to the "/credentials" endpoint.
 * This controller utilizes the {@link CredentialService} to manage user credentials.
 */
@RestController
@RequestMapping("/credentials")
@RequiredArgsConstructor
@Slf4j
public class CredentialController {
    private final CredentialService credentialService;

    /**
     * Handles HTTP POST requests to "/credentials/change-password" for changing a user's password.
     *
     * @param changePasswordDto The data transfer object containing the new password.
     * @return A ResponseEntity with an HTTP status indicating the result of the password change operation.
     */
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        String currentUsername = getCurrentUsername();
        credentialService.changePassword(currentUsername, changePasswordDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves the current username of the authenticated user.
     *
     * @return The username of the currently authenticated user.
     * @throws UsernameNotFoundException if the user is not found or not authenticated.
     */
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        throw new UsernameNotFoundException("User is not found.");
    }
}
