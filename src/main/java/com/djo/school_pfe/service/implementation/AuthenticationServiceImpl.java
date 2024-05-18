package com.djo.school_pfe.service.implementation;

import com.djo.school_pfe.dto.AuthResponseDto;
import com.djo.school_pfe.dto.LoginDto;
import com.djo.school_pfe.entity.*;
import com.djo.school_pfe.error.BadRequestException;
import com.djo.school_pfe.repository.RoleRepository;
import com.djo.school_pfe.repository.UserRepository;
import com.djo.school_pfe.security.JwtGenerator;
import com.djo.school_pfe.service.interfaces.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    Validation validation;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtGenerator jwtGenerator;
    @Value("${refresh.token.secret}")
    private String refreshTokenSecret;
    @Value("${access.token.expiration}")
    private Long accessTokenExpiration;
    @Value("${access.token.secret}")
    private String accessTokenSecret;

    @Override
    public String register(UserEntity user, String roleName) {
        if (this.userRepository.existsByUserNameOrEmailAddress(user.getUserName(), user.getEmailAddress())) {
            throw new BadRequestException("Username or email-address already used");
        }

        Role role = this.roleRepository.findByRoleName(roleName);
        user.setRoles(Collections.singletonList(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Create an Enseignant if roleName is "enseignant"
        if (roleName.equalsIgnoreCase("enseignant")) {
            Enseignant enseignant = new Enseignant();
            BeanUtils.copyProperties(user, enseignant); // Copy user properties to enseignant
            // Set additional enseignant properties if needed
            this.userRepository.save(enseignant);
        } else if (roleName.equalsIgnoreCase("admin")) {
            Admin admin = new Admin();
            BeanUtils.copyProperties(user, admin); // Copy user properties to admin
            // Set additional admin properties if needed
            this.userRepository.save(admin);
        } else if (roleName.equalsIgnoreCase("eleve")) {
            Eleve eleve = new Eleve();
            BeanUtils.copyProperties(user, eleve); // Copy user properties to admin
            // Set additional admin properties if needed
            this.userRepository.save(eleve);
        } else if (roleName.equalsIgnoreCase("parent")) {
            Parent parent = new Parent();
            BeanUtils.copyProperties(user, parent); // Copy user properties to admin
            // Set additional admin properties if needed
            this.userRepository.save(parent);
        }
        else if (roleName.equalsIgnoreCase("comptable")) {
            Comptable comptable= new Comptable();
            BeanUtils.copyProperties(user, comptable);
            this.userRepository.save(comptable);
        }

        else {
            // Handle other roles if needed
            throw new BadRequestException("Unsupported role");
        }

        return "User saved successfully";
    }


    @Override
    public AuthResponseDto login(LoginDto loginDto) {
        UserEntity userEntity = this.getUser(loginDto.getUserName());
        if (userEntity.getExpirationDate().isBefore(Instant.now())) {
            this.lockAccount(userEntity);
        }
        Authentication authentication = getAuthentication(loginDto.getUserName(), loginDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtGenerator.generateTokens(authentication);
    }

    @Override
    public AuthResponseDto refreshToken(HttpServletRequest request) {
        String token = jwtGenerator.getJWTFromRequest(request);
        if (StringUtils.hasText(token) && jwtGenerator.validateToken(token, refreshTokenSecret)) {
            UserEntity userEntity = this.getUser(jwtGenerator.getUsernameFromToken(token, refreshTokenSecret));
            if (userEntity.getExpirationDate().isBefore(Instant.now())) {
                this.lockAccount(userEntity);
                return new AuthResponseDto(null, null, "Account Expired");
            }
            return new AuthResponseDto(this.generateAccessToken(userEntity), null, "New access token");
        }
        return new AuthResponseDto(null, null, "Refresh token invalid");
    }

    @Override
    public String logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication)) SecurityContextHolder.clearContext();
        return "Logged out";
    }

    private String generateAccessToken(UserEntity userEntity) {
        Date currentDate = new Date();
        Claims claims = Jwts.claims().setSubject(userEntity.getUserName());
        claims.put("roles", userEntity.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()));
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + this.accessTokenExpiration))
                .signWith(SignatureAlgorithm.HS512, accessTokenSecret)
                .compact();
    }

    private Authentication getAuthentication(String userName, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password));
        return authentication;
    }

    private UserEntity getUser(String userName) {
        return this.userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("Bad Credential"));
    }

    private void lockAccount(UserEntity user) {
        user.setExpired(true);
        userRepository.save(user);
    }
}
