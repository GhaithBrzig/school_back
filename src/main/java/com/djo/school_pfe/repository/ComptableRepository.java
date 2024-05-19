package com.djo.school_pfe.repository;

import com.djo.school_pfe.entity.Comptable;
import com.djo.school_pfe.entity.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComptableRepository extends JpaRepository<Comptable, Long> {

    Boolean existsByUserNameOrEmailAddress(String userName, String emailAddress);

}
