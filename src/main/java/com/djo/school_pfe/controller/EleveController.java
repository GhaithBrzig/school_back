package com.djo.school_pfe.controller;

import com.djo.school_pfe.entity.Eleve;
import com.djo.school_pfe.service.interfaces.EleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eleves")
public class EleveController {
    private final EleveService eleveService;

    @Autowired
    public EleveController(EleveService eleveService) {
        this.eleveService = eleveService;
    }

    @PostMapping
    public String createEleve(@RequestBody Eleve eleve) {
        return eleveService.add(eleve);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Eleve> getEleveById(@PathVariable Long id) {
        Eleve eleve = eleveService.getEleveById(id);
        return ResponseEntity.ok(eleve);
    }

    @GetMapping
    public ResponseEntity<List<Eleve>> getAllEleves() {
        List<Eleve> eleves = eleveService.getAllEleves();
        return ResponseEntity.ok(eleves);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Eleve> updateEleve(@PathVariable Long id, @RequestBody Eleve eleve) {
        Eleve updatedEleve = eleveService.updateEleve(id, eleve);
        return ResponseEntity.ok(updatedEleve);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEleve(@PathVariable Long id) {
        eleveService.deleteEleve(id);
        return ResponseEntity.noContent().build();
    }
}
