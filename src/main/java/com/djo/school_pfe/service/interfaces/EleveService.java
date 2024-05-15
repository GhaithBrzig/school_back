package com.djo.school_pfe.service.interfaces;

import com.djo.school_pfe.entity.Classe;
import com.djo.school_pfe.entity.Eleve;
import com.djo.school_pfe.entity.UserEntity;

import java.util.List;

public interface EleveService {


    String register(UserEntity user, String roleName);


    Eleve getEleveById(Long id);
    List<Eleve> getAllEleves();
    Eleve updateEleve(Long id, Eleve eleve);
    void deleteEleve(Long id);

    String add(Eleve eleve, String roleName, Classe classe);
}
