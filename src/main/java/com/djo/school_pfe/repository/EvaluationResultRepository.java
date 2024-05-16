package com.djo.school_pfe.repository;

import com.djo.school_pfe.entity.Eleve;
import com.djo.school_pfe.entity.EvaluationResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationResultRepository extends JpaRepository<EvaluationResult, Long> {
    List<EvaluationResult> findByEleve(Eleve eleve);
}
