package com.djo.school_pfe.service.implementation;

import com.djo.school_pfe.entity.Eleve;
import com.djo.school_pfe.entity.Role;
import com.djo.school_pfe.entity.Validation;
import com.djo.school_pfe.error.BadRequestException;
import com.djo.school_pfe.repository.EleveRepository;
import com.djo.school_pfe.repository.RoleRepository;
import com.djo.school_pfe.service.interfaces.EleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    @Override
    public String add(Eleve eleve, String roleName) {


            if (this.eleveRepository.existsByUserNameOrEmailAddress(eleve.getUserName(), eleve.getEmailAddress())) {
                throw new BadRequestException("Username or email-address already used");
            }

            Role role = this.roleRepository.findByRoleName(roleName);
            eleve.setRoles(Collections.singletonList(role));
            eleve.setPassword(passwordEncoder.encode(eleve.getPassword()));

            this.eleveRepository.save(eleve);
            return "Eleve saved successfully";
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
