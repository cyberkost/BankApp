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

@RestController
@RequestMapping("/credentials")
@RequiredArgsConstructor
@Slf4j
public class CredentialController {
    private final CredentialService credentialService;
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        String currentUsername = getCurrentUsername();
        credentialService.changePassword(currentUsername, changePasswordDto);
        return ResponseEntity.ok().build();
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        throw new UsernameNotFoundException("User is not found.");
    }
}
