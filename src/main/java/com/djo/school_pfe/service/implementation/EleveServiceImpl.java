package com.djo.school_pfe.service.implementation;
import com.djo.school_pfe.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.djo.school_pfe.error.BadRequestException;
import com.djo.school_pfe.repository.EleveRepository;
import com.djo.school_pfe.repository.RoleRepository;
import com.djo.school_pfe.repository.UserRepository;
import com.djo.school_pfe.service.interfaces.EleveService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class EleveServiceImpl implements EleveService {
    private final EleveRepository eleveRepository;

    @Autowired
    public EleveServiceImpl(EleveRepository eleveRepository) {
        this.eleveRepository = eleveRepository;
    }
    @Autowired
    Validation validation;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public String add(Eleve eleve, String roleName, Classe classe) {

        if (this.eleveRepository.existsByUserNameOrEmailAddress(eleve.getUserName(), eleve.getEmailAddress())) {
            throw new BadRequestException("Username or email-address already used");
        }

        Role role = roleRepository.findByRoleName(roleName);
        eleve.setRoles(Collections.singletonList(role));
        eleve.setClasse(classe); // Set the Classe object on the Eleve
        eleve.setPassword(passwordEncoder.encode(eleve.getPassword()));
        eleveRepository.save(eleve);
        return "Eleve saved successfully";
    }

    private String generateRandomPassword() {
        // Generate a random alphanumeric password
        int passwordLength = 10;
        String alphanumericChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < passwordLength; i++) {
            password.append(alphanumericChars.charAt(random.nextInt(alphanumericChars.length())));
        }
        return password.toString();
    }
    @Override
    public String register(UserEntity user, String roleName) {

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
        } else {
            // Handle other roles if needed
            throw new BadRequestException("Unsupported role");
        }

        return "User saved successfully";
    }



    @Override
    public Eleve getEleveById(Long id) {
        return eleveRepository.findById(id).orElse(null);
    }

    @Override
    public List<Eleve> getAllEleves() {
        return eleveRepository.findAll();
    }

    @Override
    public Eleve updateEleve(Long id, Eleve eleve) {
        Optional<Eleve> optionalEleve = eleveRepository.findById(id);
        if (optionalEleve.isPresent()) {
            // Update the eleve's details
            eleve.setUserId(id); // Set the ID of the eleve to be updated
            return eleveRepository.save(eleve);
        } else {
            throw new IllegalArgumentException("Eleve with id " + id + " does not exist!");
        }
    }

    @Override
    public void deleteEleve(Long id) {
        eleveRepository.deleteById(id);
    }
}