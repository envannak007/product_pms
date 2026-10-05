package com.example.PMS.products.repository;

import com.example.PMS.products.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository< ProductEntity,Integer> {

    boolean existsByGtin(String gtin);
    Optional<ProductEntity> findByGtin(String gtin);
}
