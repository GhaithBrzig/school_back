package com.djo.school_pfe.repository;

import com.djo.school_pfe.entity.Enseignant;
import com.djo.school_pfe.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByEnseignant_UserId(Long enseignantId);
    List<Evaluation> findByClasseId(Long classeId);
}
