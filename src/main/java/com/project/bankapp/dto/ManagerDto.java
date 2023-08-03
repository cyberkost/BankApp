package com.project.bankapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ManagerDto {
    private String firstName;
    private String lastName;
    private String status;
    private String position;
}
