package com.djo.school_pfe.repository;

import com.djo.school_pfe.entity.Eleve;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EleveRepository extends JpaRepository<Eleve, Long> {

}
