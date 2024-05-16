package com.djo.school_pfe.service.implementation;

import com.djo.school_pfe.entity.Eleve;
import com.djo.school_pfe.entity.EvaluationResult;
import com.djo.school_pfe.entity.Validation;
import com.djo.school_pfe.repository.EleveRepository;
import com.djo.school_pfe.repository.EvaluationResultRepository;
import com.djo.school_pfe.repository.RoleRepository;
import com.djo.school_pfe.service.interfaces.EvaluationResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationResultServiceImpl implements EvaluationResultService {
    private final EvaluationResultRepository evaluationResultRepository;

    @Autowired
    public EvaluationResultServiceImpl(EvaluationResultRepository evaluationResultRepository) {
        this.evaluationResultRepository = evaluationResultRepository;
    }

    @Autowired
    Validation validation;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    EleveRepository eleveRepository;

    @Override
    public String add(EvaluationResult evaluationResult) {
        this.evaluationResultRepository.save(evaluationResult);
        return "EvaluationResult saved successfully";
    }

    @Override
    public EvaluationResult getEvaluationResultById(Long id) {
        return evaluationResultRepository.findById(id).orElse(null);
    }

    @Override
    public List<EvaluationResult> getAllEvaluationResults() {
        return evaluationResultRepository.findAll();
    }

    @Override
    public void deleteEvaluationResult(Long id) {
        evaluationResultRepository.deleteById(id);
    }

    @Override
    public List<EvaluationResult> getEvaluationResultsByUserId(Long userId) {
        Eleve eleve = eleveRepository.findById(userId).orElse(null); // Fetch Eleve by ID using repository
        if (eleve != null) {
            return evaluationResultRepository.findByEleve(eleve); // Pass Eleve to repository method
        } else {
            // Handle case where Eleve with given ID is not found
            return null;
        }
    }
}
