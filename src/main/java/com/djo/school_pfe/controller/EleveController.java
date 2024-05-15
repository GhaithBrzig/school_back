package com.djo.school_pfe.controller;

import com.djo.school_pfe.entity.Classe;
import com.djo.school_pfe.entity.Eleve;
import com.djo.school_pfe.error.BadRequestException;
import com.djo.school_pfe.service.interfaces.ClasseService;
import com.djo.school_pfe.service.interfaces.EleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eleves")
public class EleveController {
    private final EleveService eleveService;
    private final ClasseService classeService;

    @Autowired
    public EleveController(EleveService eleveService, ClasseService classeService) {
        this.eleveService = eleveService;
        this.classeService = classeService;
    }

    @PostMapping
    public String createEleve(@RequestParam(value = "roleName") String roleName, @RequestParam(value = "classeId") Long classeId, @RequestBody Eleve eleve) {
        Classe classe = classeService.getClasseById(classeId);
        if (classe == null) {
            throw new BadRequestException("Classe not found");
        }
        return eleveService.add(eleve, roleName, classe);
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
