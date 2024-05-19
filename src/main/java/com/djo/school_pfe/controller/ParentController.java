package com.djo.school_pfe.controller;

import com.djo.school_pfe.entity.Eleve;
import com.djo.school_pfe.entity.Parent;
import com.djo.school_pfe.service.interfaces.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/parents")
public class ParentController {
    private final ParentService parentService;

    @Autowired
    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @PostMapping
    public String createParent(@RequestParam(value = "roleName") String roleName,@RequestBody Parent parent) {
        return parentService.add(parent, roleName);
    }

    @PostMapping("/{parentId}/photo")
    public ResponseEntity<String> uploadPhoto(@PathVariable Long parentId, @RequestPart MultipartFile file) {
        try {
            parentService.uploadPhoto(parentId, file);
            return ResponseEntity.ok("Photo uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload photo");
        }
    }

    @PostMapping("/{parentId}/enfants/{enfantId}")
    public ResponseEntity<?> assignEnfantToParent(
            @PathVariable Long parentId, @PathVariable Long enfantId) {

        parentService.assignEnfantToParent(parentId, enfantId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{parentId}/enfants")
    public ResponseEntity<List<Eleve>> getElevesForParent(@PathVariable Long parentId) {
        Parent parent = parentService.getParentById(parentId);
        if (parent == null) {
            return ResponseEntity.notFound().build();
        }

        List<Eleve> eleves = parent.getEnfants();
        return ResponseEntity.ok(eleves);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Parent> getParentById(@PathVariable Long id) {
        Parent parent = parentService.getParentById(id);
        return ResponseEntity.ok(parent);
    }

    @GetMapping
    public ResponseEntity<List<Parent>> getAllParents() {
        List<Parent> parents = parentService.getAllParents();
        return ResponseEntity.ok(parents);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parent> updateParent(@PathVariable Long id, @RequestBody Parent parent) {
        Parent updatedParent = parentService.updateParent(id, parent);
        return ResponseEntity.ok(updatedParent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable Long id) {
        parentService.deleteParent(id);
        return ResponseEntity.noContent().build();
    }
}
