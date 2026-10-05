package com.example.PMS.carts.repository;

import com.example.PMS.carts.entitty.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity,Integer> {
    Optional<CartEntity> findByUserId(Integer userId);
}
