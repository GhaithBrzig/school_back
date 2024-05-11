package com.djo.school_pfe.service.implementation;

import com.djo.school_pfe.entity.Enseignant;
import com.djo.school_pfe.entity.Role;
import com.djo.school_pfe.entity.Validation;
import com.djo.school_pfe.error.BadRequestException;
import com.djo.school_pfe.repository.EnseignantRepository;
import com.djo.school_pfe.repository.RoleRepository;
import com.djo.school_pfe.service.interfaces.EnseignantService;
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
    @Override
    public String add(Enseignant enseignant, String roleName) {


        if (this.enseignantRepository.existsByUserNameOrEmailAddress(enseignant.getUserName(), enseignant.getEmailAddress())) {
            throw new BadRequestException("Username or email-address already used");
        }

        Role role = this.roleRepository.findByRoleName(roleName);
        enseignant.setRoles(Collections.singletonList(role));
        enseignant.setPassword(passwordEncoder.encode(enseignant.getPassword()));

        this.enseignantRepository.save(enseignant);
        return "Enseignant saved successfully";
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
