package com.djo.school_pfe.controller;

import com.djo.school_pfe.dto.AuthResponseDto;
import com.djo.school_pfe.dto.LoginDto;
import com.djo.school_pfe.entity.UserEntity;
import com.djo.school_pfe.service.interfaces.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/authentication")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/register")
    private String register(@RequestParam(value = "roleName") String roleName, @RequestBody UserEntity user) {
        return this.authenticationService.register(user, roleName);
    }

    @PostMapping("/login")
    private AuthResponseDto login(@RequestBody LoginDto loginDto) {
        return authenticationService.login(loginDto);
    }

    @PostMapping("/refresh")
    private AuthResponseDto refreshToken(HttpServletRequest request) {
        return this.authenticationService.refreshToken(request);
    }

    @PostMapping("/logout")
    private String logout() {
        return this.authenticationService.logout();
    }
}
