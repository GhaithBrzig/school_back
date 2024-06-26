package com.djo.school_pfe.service.implementation;

import com.djo.school_pfe.entity.Classe;
import com.djo.school_pfe.entity.Evaluation;
import com.djo.school_pfe.entity.Role;
import com.djo.school_pfe.entity.Validation;
import com.djo.school_pfe.error.BadRequestException;
import com.djo.school_pfe.repository.ClasseRepository;
import com.djo.school_pfe.repository.EvaluationRepository;
import com.djo.school_pfe.repository.RoleRepository;
import com.djo.school_pfe.service.interfaces.ClasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ClasseServiceImpl implements ClasseService {
    private final ClasseRepository classeRepository;
    private final EvaluationRepository evaluationRepository;

    @Autowired
    public ClasseServiceImpl(ClasseRepository classeRepository, EvaluationRepository evaluationRepository) {
        this.classeRepository = classeRepository;
        this.evaluationRepository = evaluationRepository;
    }
    @Autowired
    Validation validation;
    @Override
    public String add(Classe classe) {
        this.classeRepository.save(classe);
        return "Classe saved successfully";
    }



    @Override
    public Classe getClasseById(Long id) {
        return classeRepository.findClasseById(id);
    }

    @Override
    public List<Classe> getAllClasses() {
        return classeRepository.findAll();
    }

    @Override
    public void assignEvaluationToClasse(Long classeId, Long evaluationId) {
        Classe classe = classeRepository.findById(classeId).orElseThrow(() -> new EntityNotFoundException("Classe not found"));
        Evaluation evaluation = evaluationRepository.findById(evaluationId).orElseThrow(() -> new EntityNotFoundException("Evaluation not found"));

        classe.addEvaluation(evaluation);

        classeRepository.save(classe);
    }

    @Override
    public Classe updateClasse(Long id, Classe updatedClasse) {
        Optional<Classe> optionalClasse = classeRepository.findById(id);
        if (optionalClasse.isPresent()) {
            Classe existingClasse = optionalClasse.get();

            // Update only the non-null fields
            if (updatedClasse.getNiveau() != null) {
                existingClasse.setNiveau(updatedClasse.getNiveau());
            }
            if (updatedClasse.getNom() != null) {
                existingClasse.setNom(updatedClasse.getNom());
            }
            if (updatedClasse.getNbrEleves() != 0) { // assuming nbrEleves is never meant to be 0 when updated
                existingClasse.setNbrEleves(updatedClasse.getNbrEleves());
            }
            if (updatedClasse.getEvaluations() != null && !updatedClasse.getEvaluations().isEmpty()) {
                existingClasse.setEvaluations(updatedClasse.getEvaluations());
            }
            if (updatedClasse.getEleves() != null && !updatedClasse.getEleves().isEmpty()) {
                existingClasse.setEleves(updatedClasse.getEleves());
            }
            if (updatedClasse.getEnseignants() != null && !updatedClasse.getEnseignants().isEmpty()) {
                existingClasse.setEnseignants(updatedClasse.getEnseignants());
            }

            return classeRepository.save(existingClasse);
        } else {
            throw new IllegalArgumentException("Classe with id " + id + " does not exist!");
        }
    }


    @Override
    public void deleteClasse(Long id) {
        classeRepository.deleteById(id);
    }
}
