package com.project.bankapp.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) class representing client information.
 * This class is used to transfer client-related data between layers of the application.
 */
@Data
@Builder
public class ClientDto {
    private String managerUuid;
    private String status;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
}
