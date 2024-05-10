package com.djo.school_pfe.service.interfaces;

import com.djo.school_pfe.entity.Eleve;

import java.util.List;

public interface EleveService {



    Eleve getEleveById(Long id);
    List<Eleve> getAllEleves();
    Eleve updateEleve(Long id, Eleve eleve);
    void deleteEleve(Long id);

    String add(Eleve eleve, String roleName);
}
