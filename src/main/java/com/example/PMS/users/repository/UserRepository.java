package com.example.PMS.users.repository;

import com.example.PMS.users.entitty.UserEntity;
import com.example.PMS.users.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<UserEntity> findByRoles_Name(RoleType name);
}
