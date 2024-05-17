package com.djo.school_pfe.repository;

import com.djo.school_pfe.entity.Classe;
import com.djo.school_pfe.entity.Enseignant;
import com.djo.school_pfe.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {

    @Query("SELECT c FROM Classe c WHERE c.id = :id")
    Classe findClasseById(@Param("id") Long id);

    List<Classe> findByEnseignantsContains(Enseignant enseignant);

    @Query("SELECT e FROM Classe c JOIN c.evaluations e WHERE c.id = :classeId")
    List<Evaluation> getEvaluationsByClasseId(@Param("classeId") Long classeId);


}
