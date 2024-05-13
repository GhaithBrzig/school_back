package com.djo.school_pfe.service.interfaces;

import com.djo.school_pfe.entity.Enseignant;
import com.djo.school_pfe.entity.Evaluation;

import java.util.List;

public interface EvaluationService {



    Evaluation getEvaluationById(Long id);
    List<Evaluation> getAllEvaluations();
    Evaluation updateEvaluation(Long id, Evaluation evaluation);
    void deleteEvaluation(Long id);

    String add(Evaluation evaluation);
    List<Evaluation> getEvaluationsByEnseignantId(Long enseignantId);
}
