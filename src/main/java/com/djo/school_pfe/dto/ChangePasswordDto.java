package com.djo.school_pfe.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private String newPassword;
    private String confirmation;
}
