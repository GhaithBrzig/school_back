package com.djo.school_pfe.controller;

import com.djo.school_pfe.entity.Classe;
import com.djo.school_pfe.entity.Evaluation;
import com.djo.school_pfe.repository.ClasseRepository;
import com.djo.school_pfe.service.interfaces.ClasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
public class ClasseController {
    private final ClasseService classeService;
    private final ClasseRepository classeRepository;


    @Autowired
    public ClasseController(ClasseService classeService, ClasseRepository classeRepository) {
        this.classeService = classeService;
        this.classeRepository = classeRepository;
    }

    @PostMapping
    public String createClasse(@RequestBody Classe classe) {
        return classeService.add(classe);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classe> getClasseById(@PathVariable Long id) {
        Classe classe = classeService.getClasseById(id);
        return ResponseEntity.ok(classe);
    }


    @PostMapping("/{classeId}/evaluations/{evaluationId}")
    public ResponseEntity<Void> assignEvaluationToClasse(@PathVariable Long classeId, @PathVariable Long evaluationId) {
        classeService.assignEvaluationToClasse(classeId, evaluationId);
        return ResponseEntity.ok().build();
    }




    @GetMapping
    public ResponseEntity<List<Classe>> getAllClasses() {
        List<Classe> classes = classeService.getAllClasses();
        return ResponseEntity.ok(classes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Classe> updateClasse(@PathVariable Long id, @RequestBody Classe classe) {
        Classe updatedClasse = classeService.updateClasse(id, classe);
        return ResponseEntity.ok(updatedClasse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable Long id) {
        classeService.deleteClasse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{classeId}/evaluations")
    public ResponseEntity<List<Evaluation>> getEvaluationsByClasseId(@PathVariable Long classeId) {
        List<Evaluation> evaluations = classeRepository.getEvaluationsByClasseId(classeId);
        return new ResponseEntity<>(evaluations, HttpStatus.OK);
    }
}
