package com.djo.school_pfe.repository;

import com.djo.school_pfe.entity.Eleve;
import com.djo.school_pfe.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
    Boolean existsByUserNameOrEmailAddress(String userName, String emailAddress);

    Optional<Eleve> findByUserName(String userName);

    Optional<Eleve> findByEmailAddress(String emailAddress);
}
