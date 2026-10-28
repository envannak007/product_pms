package com.example.PMS.products.entity;

import com.example.PMS.category.entity.CategoryEntity;
import com.example.PMS.products.enums.DiscountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",nullable = false)
    private CategoryEntity category;

    @Column(nullable = false,length = 20)
    @Enumerated(EnumType.STRING)
    private DiscountType discountType = DiscountType.NONE;

    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal discountValue = BigDecimal.ZERO;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<ProductImageEntity> image = new ArrayList<>();

    @Column(nullable = false,unique = true,length = 13)
    private String gtin;
}
