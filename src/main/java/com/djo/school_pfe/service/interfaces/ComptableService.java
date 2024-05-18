package com.djo.school_pfe.service.interfaces;


import com.djo.school_pfe.entity.*;

import java.util.List;

public interface ComptableService {


    String register(UserEntity user, String roleName);

    Comptable getComptableById(Long id);
    List<Comptable> getAllComptables();
    void deleteComptable(Long id);
    String add(Comptable comptable, String roleName);
}

