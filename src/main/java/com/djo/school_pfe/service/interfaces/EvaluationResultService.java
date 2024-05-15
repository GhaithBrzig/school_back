package com.djo.school_pfe.service.interfaces;

import com.djo.school_pfe.entity.EvaluationResult;

import java.util.List;

public interface EvaluationResultService {



    EvaluationResult getEvaluationResultById(Long id);
    List<EvaluationResult> getAllEvaluationResults();

    void deleteEvaluationResult(Long id);

    String add(EvaluationResult evaluationResult);
}
