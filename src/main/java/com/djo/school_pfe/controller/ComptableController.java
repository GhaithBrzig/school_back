package com.djo.school_pfe.controller;

import com.djo.school_pfe.entity.Comptable;
import com.djo.school_pfe.entity.Parent;
import com.djo.school_pfe.entity.PhotoState;
import com.djo.school_pfe.service.interfaces.ComptableService;
import com.djo.school_pfe.service.interfaces.ParentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/comptables")
public class ComptableController {

    private final ComptableService comptableService;

    public ComptableController(ComptableService comptableService) {
        this.comptableService = comptableService;
    }

    @PostMapping
    public String createComptable(@RequestParam(value = "roleName") String roleName, @RequestBody Comptable comptable) {
        return comptableService.add(comptable, roleName);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comptable> getComptableById(@PathVariable Long id) {
        Comptable comptable = comptableService.getComptableById(id);
        return ResponseEntity.ok(comptable);
    }

    @GetMapping
    public ResponseEntity<List<Comptable>> getAllComptables() {
        List<Comptable> comptables = comptableService.getAllComptables();
        return ResponseEntity.ok(comptables);
    }

    @PutMapping("/{comptableId}/parents/{parentId}/photo-state")
    public ResponseEntity<Void> updateParentPhotoState(
            @PathVariable Long comptableId,
            @PathVariable Long parentId,
            @RequestParam PhotoState photoState) {

        comptableService.updateParentPhotoState(parentId, comptableId, photoState);
        return ResponseEntity.noContent().build();
    }

}
