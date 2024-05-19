package com.djo.school_pfe;

import com.djo.school_pfe.entity.Role;
import com.djo.school_pfe.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@SpringBootApplication
public class SchoolPfeApplication {

    @Autowired
    private RoleRepository roleRepository;


    public static void main(String[] args) {
        SpringApplication.run(SchoolPfeApplication.class, args);
        System.out.println("applicationStarted");
    }

    @Bean
    ApplicationRunner init() {
        return args -> {
            List<String> roleNames = Arrays.asList("admin", "parent", "eleve", "enseignant", "comptable");
            for (String roleName : roleNames) {
                Role existingRole = roleRepository.findByRoleName(roleName);
                if (existingRole == null) {
                    Role newRole = new Role();
                    newRole.setRoleName(roleName);
                    roleRepository.save(newRole);
                }
            }
        };
    }
}
