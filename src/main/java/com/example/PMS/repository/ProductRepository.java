package com.example.PMS.repository;

import com.example.PMS.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository< ProductEntity,Integer> {
}
