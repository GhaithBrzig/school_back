package com.djo.school_pfe.controller;

import com.djo.school_pfe.entity.Classe;
import com.djo.school_pfe.entity.Eleve;
import com.djo.school_pfe.entity.Enseignant;
import com.djo.school_pfe.entity.Matiere;
import com.djo.school_pfe.error.BadRequestException;
import com.djo.school_pfe.repository.ClasseRepository;
import com.djo.school_pfe.repository.EnseignantRepository;
import com.djo.school_pfe.service.interfaces.ClasseService;
import com.djo.school_pfe.service.interfaces.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enseignants")
public class EnseignantController {
    private final EnseignantService enseignantService;
    private final ClasseService classeService;
    @Autowired
    EnseignantRepository enseignantRepository;
    @Autowired
    ClasseRepository classeRepository;

    @Autowired
    public EnseignantController(EnseignantService enseignantService,ClasseService classeService) {
        this.enseignantService = enseignantService;
        this.classeService = classeService;

    }
    @GetMapping("/{enseignantId}/classes")
    public List<Classe> getClassesByEnseignantId(@PathVariable Long enseignantId) {
        return enseignantService.getClassesByEnseignantId(enseignantId);
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

    @PutMapping("/{classeId}/enseignants/{enseignantId}")
    public ResponseEntity<Classe> addEnseignantToClasse(@PathVariable Long classeId, @PathVariable Long enseignantId) {
        Optional<Classe> optionalClasse = classeRepository.findById(classeId);
        if (!optionalClasse.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Enseignant> optionalEnseignant = enseignantRepository.findById(enseignantId);
        if (!optionalEnseignant.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Classe classe = optionalClasse.get();
        Enseignant enseignant = optionalEnseignant.get();

        // Check if the enseignant is already associated with the classe
        if (classe.getEnseignants().contains(enseignant)) {
            return ResponseEntity.badRequest().build();
        }

        // Add the enseignant to the classe's list of enseignants
        classe.getEnseignants().add(enseignant);

        // Save the updated classe to the database
        classe = classeRepository.save(classe);

        return ResponseEntity.ok(classe);
    }
}
