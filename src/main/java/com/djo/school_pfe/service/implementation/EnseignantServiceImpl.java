package com.djo.school_pfe.service.implementation;

import com.djo.school_pfe.entity.*;
import com.djo.school_pfe.error.BadRequestException;
import com.djo.school_pfe.repository.EnseignantRepository;
import com.djo.school_pfe.repository.RoleRepository;
import com.djo.school_pfe.repository.UserRepository;
import com.djo.school_pfe.service.interfaces.EnseignantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EnseignantServiceImpl implements EnseignantService {
    private final EnseignantRepository enseignantRepository;

    @Autowired
    public EnseignantServiceImpl(EnseignantRepository enseignantRepository) {
        this.enseignantRepository = enseignantRepository;
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
    public String add(Enseignant enseignant, String roleName, Classe classe, Matiere matiere) {

        if (this.enseignantRepository.existsByUserNameOrEmailAddress(enseignant.getUserName(), enseignant.getEmailAddress())) {
            throw new BadRequestException("Username or email-address already used");
        }


        Role role = roleRepository.findByRoleName(roleName);
        enseignant.setRoles(Collections.singletonList(role));
        enseignant.setMatiere(matiere);
        enseignant.setClasse(classe);
        enseignant.setPassword(passwordEncoder.encode(enseignant.getPassword()));
        enseignantRepository.save(enseignant);
        return "Enseignant saved successfully";
    }



    @Override
    public String register(UserEntity user, String roleName,Matiere matiere) {

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
    public Enseignant getEnseignantById(Long id) {
        return enseignantRepository.findById(id).orElse(null);
    }

    @Override
    public List<Enseignant> getAllEnseignants() {
        return enseignantRepository.findAll();
    }

    @Override
    public Enseignant updateEnseignant(Long id, Enseignant enseignant) {
        Optional<Enseignant> optionalEnseignant = enseignantRepository.findById(id);
        if (optionalEnseignant.isPresent()) {
            // Update the enseignant's details
            enseignant.setUserId(id); // Set the ID of the enseignant to be updated
            return enseignantRepository.save(enseignant);
        } else {
            throw new IllegalArgumentException("Enseignant with id " + id + " does not exist!");
        }
    }

    @Override
    public void deleteEnseignant(Long id) {
        enseignantRepository.deleteById(id);
    }
}
