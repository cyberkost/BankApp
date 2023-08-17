package com.project.bankapp.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) class representing client registration information.
 * This class encapsulates the data required for registering a new client.
 */
@Data
@Builder
public class ClientRegistrationDto {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String email;
    private String password;
}
