package com.djo.school_pfe.service.interfaces;

import com.djo.school_pfe.entity.Enseignant;

import java.util.List;

public interface EnseignantService {



    Enseignant getEnseignantById(Long id);
    List<Enseignant> getAllEnseignants();
    Enseignant updateEnseignant(Long id, Enseignant enseignant);
    void deleteEnseignant(Long id);

    String add(Enseignant enseignant, String roleName);
}
