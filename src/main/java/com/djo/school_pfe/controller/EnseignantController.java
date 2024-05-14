package com.djo.school_pfe.controller;

import com.djo.school_pfe.entity.Classe;
import com.djo.school_pfe.entity.Eleve;
import com.djo.school_pfe.entity.Enseignant;
import com.djo.school_pfe.entity.Matiere;
import com.djo.school_pfe.error.BadRequestException;
import com.djo.school_pfe.service.interfaces.ClasseService;
import com.djo.school_pfe.service.interfaces.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enseignants")
public class EnseignantController {
    private final EnseignantService enseignantService;
    private final ClasseService classeService;

    @Autowired
    public EnseignantController(EnseignantService enseignantService,ClasseService classeService) {
        this.enseignantService = enseignantService;
        this.classeService = classeService;

    }

    @PostMapping
    public String createEnseignant(@RequestParam(value = "roleName") String roleName, @RequestParam(value = "classeId") Long classeId, @RequestParam(value = "matiere") String matiere, @RequestBody Enseignant enseignant) {
        Classe classe = classeService.getClasseById(classeId);
        if (classe == null) {
            throw new BadRequestException("Classe not found");
        }

        // Convert the matiere string to a Matiere enum value
        Matiere matiereEnum = Matiere.valueOf(matiere.toUpperCase());

        // Call the EnseignantService.add method with the matiere enum value
        return enseignantService.add(enseignant, roleName, classe, matiereEnum);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enseignant> getEnseignantById(@PathVariable Long id) {
        Enseignant enseignant = enseignantService.getEnseignantById(id);
        return ResponseEntity.ok(enseignant);
    }

    @GetMapping
    public ResponseEntity<List<Enseignant>> getAllEnseignants() {
        List<Enseignant> enseignants = enseignantService.getAllEnseignants();
        return ResponseEntity.ok(enseignants);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enseignant> updateEnseignant(@PathVariable Long id, @RequestBody Enseignant enseignant) {
        Enseignant updatedEnseignant = enseignantService.updateEnseignant(id, enseignant);
        return ResponseEntity.ok(updatedEnseignant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnseignant(@PathVariable Long id) {
        enseignantService.deleteEnseignant(id);
        return ResponseEntity.noContent().build();
    }
}
