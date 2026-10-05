package com.example.PMS.rating.repository;

import com.example.PMS.rating.entitty.ProductRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRatingRepository extends JpaRepository<ProductRatingEntity,Integer> {
    Optional<ProductRatingEntity> findByUserIdAndProductId(
            Integer userId,
            Integer productId
    );

    List<ProductRatingEntity> findByProductIdOrderByCreateAtDesc(
            Integer productId
    );

    @Query("""
            SELECT COALESCE(AVG(r.rating), 0.0)
            FROM ProductRatingEntity r
            WHERE r.product.id = :productId
            """)
    Double getAverageRating(
            @Param("productId") Integer productId
    );

    @Query("""
            SELECT r.rating, COUNT(r)
            FROM ProductRatingEntity r
            WHERE r.product.id = :productId
            GROUP BY r.rating
            """)
    List<Object[]> getRatingCounts(
            @Param("productId") Integer productId
    );
}
