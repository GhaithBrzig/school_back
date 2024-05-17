package com.djo.school_pfe.service.interfaces;

import com.djo.school_pfe.entity.Classe;
import com.djo.school_pfe.entity.Evaluation;

import java.util.List;

public interface ClasseService {

    public void assignEvaluationToClasse(Long classeId, Long evaluationId);

    Classe getClasseById(Long id);
    List<Classe> getAllClasses();
    Classe updateClasse(Long id, Classe classe);
    void deleteClasse(Long id);

    String add(Classe classe);
}
