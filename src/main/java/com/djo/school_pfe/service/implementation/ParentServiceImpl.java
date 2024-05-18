package com.djo.school_pfe.service.implementation;

import com.djo.school_pfe.entity.*;
import com.djo.school_pfe.error.BadRequestException;
import com.djo.school_pfe.repository.EleveRepository;
import com.djo.school_pfe.repository.ParentRepository;
import com.djo.school_pfe.repository.RoleRepository;
import com.djo.school_pfe.service.interfaces.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ParentServiceImpl implements ParentService {
    private final ParentRepository parentRepository;

    @Autowired
    public ParentServiceImpl(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }
    @Autowired
    Validation validation;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private EleveRepository eleveRepository;
    @Override
    public String add(Parent parent, String roleName) {


        if (this.parentRepository.existsByUserNameOrEmailAddress(parent.getUserName(), parent.getEmailAddress())) {
            throw new BadRequestException("Username or email-address already used");
        }

        Role role = this.roleRepository.findByRoleName(roleName);
        parent.setRoles(Collections.singletonList(role));
        parent.setPassword(passwordEncoder.encode(parent.getPassword()));

        this.parentRepository.save(parent);
        return "Parent saved successfully";
    }


    public void assignEnfantToParent(Long parentId, Long enfantId) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found"));

        Eleve enfant = eleveRepository.findById(enfantId)
                .orElseThrow(() -> new ResourceNotFoundException("Enfant not found"));

        // Check if the parent is already in the eleve's list of parents
        if (!enfant.getParents().contains(parent)) {
            enfant.getParents().add(parent);
            parent.getEnfants().add(enfant);

            eleveRepository.save(enfant);
            parentRepository.save(parent);
        } else {
            throw new BadRequestException("Parent is already assigned to the eleve");
        }
    };

    @Override
    public void uploadPhoto(Long parentId, MultipartFile file) throws IOException {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        byte[] bytes = file.getBytes();
        parent.setPhoto(bytes);
        parent.setPhotoState(PhotoState.PAS_TRAITER);

        parentRepository.save(parent);
    }




    @Override
    public Parent getParentById(Long id) {
        return parentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    @Override
    public Parent updateParent(Long id, Parent parent) {
        Optional<Parent> optionalParent = parentRepository.findById(id);
        if (optionalParent.isPresent()) {
            // Update the parent's details
            parent.setUserId(id); // Set the ID of the parent to be updated
            return parentRepository.save(parent);
        } else {
            throw new IllegalArgumentException("Parent with id " + id + " does not exist!");
        }
    }

    @Override
    public void deleteParent(Long id) {
        parentRepository.deleteById(id);
    }
    }
