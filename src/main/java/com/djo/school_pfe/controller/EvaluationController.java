package com.djo.school_pfe.controller;

import com.djo.school_pfe.entity.Evaluation;
import com.djo.school_pfe.service.interfaces.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {
    private final EvaluationService evaluationService;

    @Autowired
    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PostMapping
    public String createEvaluation(@RequestBody Evaluation evaluation) {

        return evaluationService.add(evaluation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evaluation> getEvaluationById(@PathVariable Long id) {
        Evaluation evaluation = evaluationService.getEvaluationById(id);
        return ResponseEntity.ok(evaluation);
    }

    @GetMapping
    public ResponseEntity<List<Evaluation>> getAllEvaluations() {
        List<Evaluation> evaluations = evaluationService.getAllEvaluations();
        return ResponseEntity.ok(evaluations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evaluation> updateEvaluation(@PathVariable Long id, @RequestBody Evaluation evaluation) {
        Evaluation updatedEvaluation = evaluationService.updateEvaluation(id, evaluation);
        return ResponseEntity.ok(updatedEvaluation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) {
        evaluationService.deleteEvaluation(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/enseignant/{enseignantId}")
    public ResponseEntity<List<Evaluation>> getEvaluationsByEnseignant(@PathVariable Long enseignantId) {
        // Assuming you have a service method to get evaluations by enseignant ID
        List<Evaluation> evaluations = evaluationService.getEvaluationsByEnseignantId(enseignantId);
        return ResponseEntity.ok(evaluations);
    }

    @GetMapping("/classe/{classeId}")
    public ResponseEntity<List<Evaluation>> getEvaluationByCLasse(@PathVariable Long classeId) {
        // Assuming you have a service method to get evaluations by enseignant ID
        List<Evaluation> evaluations = evaluationService.getEvaluationsByClassId(classeId);
        return ResponseEntity.ok(evaluations);
    }
}
