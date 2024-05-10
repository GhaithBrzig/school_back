package com.djo.school_pfe.service.interfaces;

import com.djo.school_pfe.dto.AuthResponseDto;
import com.djo.school_pfe.dto.LoginDto;
import com.djo.school_pfe.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    String register(UserEntity user, String roleName);

    AuthResponseDto login(LoginDto loginDto);

    AuthResponseDto refreshToken(HttpServletRequest request);

    String logout();
}
