package com.djo.school_pfe.service.interfaces;

import com.djo.school_pfe.entity.Classe;

import java.util.List;

public interface ClasseService {



    Classe getClasseById(Long id);
    List<Classe> getAllClasses();
    Classe updateClasse(Long id, Classe classe);
    void deleteClasse(Long id);

    String add(Classe classe);
}
