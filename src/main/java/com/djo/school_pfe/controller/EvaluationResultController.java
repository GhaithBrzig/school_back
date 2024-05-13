package com.djo.school_pfe.controller;

import com.djo.school_pfe.entity.EvaluationResult;
import com.djo.school_pfe.service.interfaces.EvaluationResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluationResults")
public class EvaluationResultController {
    private final EvaluationResultService evaluationResultService;

    @Autowired
    public EvaluationResultController(EvaluationResultService evaluationResultService) {
        this.evaluationResultService = evaluationResultService;
    }

    @PostMapping
    public String createEvaluationResult(@RequestBody EvaluationResult evaluationResult) {
        return evaluationResultService.add(evaluationResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluationResult> getEvaluationResultById(@PathVariable Long id) {
        EvaluationResult evaluationResult = evaluationResultService.getEvaluationResultById(id);
        return ResponseEntity.ok(evaluationResult);
    }

    @GetMapping
    public ResponseEntity<List<EvaluationResult>> getAllEvaluationResults() {
        List<EvaluationResult> evaluationResults = evaluationResultService.getAllEvaluationResults();
        return ResponseEntity.ok(evaluationResults);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluationResult(@PathVariable Long id) {
        evaluationResultService.deleteEvaluationResult(id);
        return ResponseEntity.noContent().build();
    }
}
