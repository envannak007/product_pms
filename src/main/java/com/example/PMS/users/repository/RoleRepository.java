package com.example.PMS.users.repository;

import com.example.PMS.users.entitty.RoleEntity;
import com.example.PMS.users.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity,Integer> {
    Optional<RoleEntity> findByName(RoleType name);
}
