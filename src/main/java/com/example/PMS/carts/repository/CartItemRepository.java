package com.example.PMS.carts.repository;

import com.example.PMS.carts.entitty.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItemEntity,Integer> {
    Optional<CartItemEntity> findByCartIdAndProductId(Integer cartId,Integer productId);
}
