package com.example.PMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_image")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 500)
    private String imageUrl;

    @Column(nullable = false,length = 500)
    private String publicId;

    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private ProductEntity product;
}
