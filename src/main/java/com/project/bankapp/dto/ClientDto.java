package com.project.bankapp.dto;

import lombok.Builder;
import lombok.Data;

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
