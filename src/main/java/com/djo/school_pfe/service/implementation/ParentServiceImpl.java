package com.djo.school_pfe.service.implementation;

import com.djo.school_pfe.entity.Parent;
import com.djo.school_pfe.entity.Role;
import com.djo.school_pfe.entity.Validation;
import com.djo.school_pfe.error.BadRequestException;
import com.djo.school_pfe.repository.ParentRepository;
import com.djo.school_pfe.repository.RoleRepository;
import com.djo.school_pfe.service.interfaces.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
