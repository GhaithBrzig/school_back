package com.djo.school_pfe.service.implementation;
import com.djo.school_pfe.entity.*;
import com.djo.school_pfe.repository.*;
import com.djo.school_pfe.service.interfaces.ComptableService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.djo.school_pfe.error.BadRequestException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ComptableServiceImpl implements ComptableService {

    @Autowired
    public ComptableServiceImpl(ComptableRepository comptableRepository) {
        this.comptableRepository = comptableRepository;
    }
    @Autowired
    Validation validation;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ComptableRepository comptableRepository;
    @Autowired
    ParentRepository parentRepository;





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
        }
        else if (roleName.equalsIgnoreCase("comptable")) {
            Comptable comptable = new Comptable();
            BeanUtils.copyProperties(user, comptable); // Copy user properties to admin
            // Set additional admin properties if needed
            this.userRepository.save(comptable);
        }
        else {
            // Handle other roles if needed
            throw new BadRequestException("Unsupported role");
        }

        return "User saved successfully";
    }


    @Override
    public void updateParentPhotoState(Long parentId, PhotoState photoState) {
        // Check if the comptable exists


        // Check if the parent exists
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found"));

        // Update the parent's photo state
        parent.setPhotoState(photoState);
        parentRepository.save(parent);
    }

    @Override
    public Comptable getComptableById(Long id) {
        return comptableRepository.findById(id).orElse(null);
    }

    @Override
    public List<Comptable> getAllComptables() {
        return comptableRepository.findAll();
    }

    @Override
    public void deleteComptable(Long id) {

        comptableRepository.deleteById(id);

    }

    @Override
    public String add(Comptable comptable, String roleName) {

        if (this.comptableRepository.existsByUserNameOrEmailAddress(comptable.getUserName(), comptable.getEmailAddress())) {
            throw new BadRequestException("Username or email-address already used");
        }

        Role role = roleRepository.findByRoleName(roleName);
        comptable.setRoles(Collections.singletonList(role));
        comptable.setPassword(passwordEncoder.encode(comptable.getPassword()));
        comptableRepository.save(comptable);
        return "Comptable saved successfully";
    }
}