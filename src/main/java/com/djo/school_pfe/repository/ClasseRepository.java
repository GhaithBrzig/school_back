package com.djo.school_pfe.repository;

import com.djo.school_pfe.entity.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {

    @Query("SELECT c FROM Classe c WHERE c.id = :id")
    Classe findClasseById(@Param("id") Long id);
}
