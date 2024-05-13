package com.djo.school_pfe.service.interfaces;

import com.djo.school_pfe.dto.ChangePasswordDto;
import com.djo.school_pfe.dto.ResetPasswordDto;
import com.djo.school_pfe.dto.UpdateProfileDto;
import com.djo.school_pfe.entity.UserEntity;

import java.util.List;

public interface AccountService {

    String forgetPassword(String emailAddress);

    String resetPassword(ResetPasswordDto resetPasswordDto);

    List<UserEntity> listUserAccounts();

    String enableAccount(Long userId);

    String disableAccount(Long userId);

    UserEntity getProfile(String userName);
    String addPassedEvaluation(Long userId, Long evaluationId);
    String updateProfile(String userName, UpdateProfileDto updateProfileDto);

    String changePassword(String userName, ChangePasswordDto changePasswordDto);
}
