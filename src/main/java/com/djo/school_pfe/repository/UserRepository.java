package com.djo.school_pfe.repository;

import com.djo.school_pfe.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByUserNameOrEmailAddress(String userName, String emailAddress);

    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByEmailAddress(String emailAddress);
}
