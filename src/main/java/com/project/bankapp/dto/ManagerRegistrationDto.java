package com.project.bankapp.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) class representing the registration information for a manager.
 * This class encapsulates the necessary attributes for registering a new manager.
 */
@Data
@Builder
public class ManagerRegistrationDto {
    private String firstName;
    private String lastName;
    private String description;
    private String login;
    private String password;
}
