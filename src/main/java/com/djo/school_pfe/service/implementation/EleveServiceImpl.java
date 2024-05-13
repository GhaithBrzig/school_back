package com.djo.school_pfe.service.implementation;

import com.djo.school_pfe.entity.Classe;
import com.djo.school_pfe.entity.Eleve;
import com.djo.school_pfe.entity.Role;
import com.djo.school_pfe.entity.Validation;
import com.djo.school_pfe.error.BadRequestException;
import com.djo.school_pfe.repository.ClasseRepository;
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
    private final ClasseRepository classeRepository;
    @Autowired
    public EleveServiceImpl(EleveRepository eleveRepository, ClasseRepository classeRepository) {
        this.eleveRepository = eleveRepository;
        this.classeRepository = classeRepository;
    }

    @Override
    public String add(Eleve eleve, Long classeId) {
        Optional<Classe> optionalClasse = classeRepository.findById(classeId);
        if (optionalClasse.isPresent()) {
            eleve.setClasse(optionalClasse.get());
            eleveRepository.save(eleve);
            return "Student saved successfully";
        } else {
            throw new IllegalArgumentException("Classe with id " + classeId + " does not exist!");
        }
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
            Eleve existingEleve = optionalEleve.get();
            existingEleve.setFirstName(eleve.getFirstName());
            existingEleve.setLastName(eleve.getLastName());
            existingEleve.setEmailAddress(eleve.getEmailAddress());
            existingEleve.setPhoneNumber(eleve.getPhoneNumber());

            // Check if the Classe object is not null
            if (eleve.getClasse() != null) {
                Long classeId = eleve.getClasse().getId();
                if (classeId != null) {
                    Optional<Classe> optionalClasse = Optional.ofNullable(classeRepository.findClasseById(classeId));
                    if (optionalClasse.isPresent()) {
                        existingEleve.setClasse(optionalClasse.get());
                    } else {
                        throw new IllegalArgumentException("Classe with id " + classeId + " does not exist!");
                    }
                } else {
                    throw new IllegalArgumentException("Classe id cannot be null!");
                }
            } else {
                // Set the Classe to null if the incoming Eleve object does not have a Classe
                existingEleve.setClasse(null);
            }

            existingEleve.setParents(eleve.getParents());
            existingEleve.setEvaluations(eleve.getEvaluations());

            return eleveRepository.save(existingEleve);
        } else {
            throw new IllegalArgumentException("Eleve with id " + id + " does not exist!");
        }
    }

    @Override
    public void deleteEleve(Long id) {
        eleveRepository.deleteById(id);
    }
}
