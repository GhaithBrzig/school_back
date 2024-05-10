package com.djo.school_pfe.dto;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String confirmationKeyValue;
    private String newPassword;
    private String confirmedNewPassword;
}
