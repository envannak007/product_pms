package com.example.PMS.repository;

import com.example.PMS.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImageEntity,Integer> {
    List<ProductImageEntity> findByProductId(Integer publicId);
}
