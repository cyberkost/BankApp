package com.project.bankapp.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;
}
