package com.example.PMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_date",nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "total_amount" ,nullable = false,precision = 10,scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false,length = 50)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity user;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @PrePersist
    public void perPersist(){
        LocalDateTime now = LocalDateTime.now();
        if (orderDate == null){
            orderDate = now;
        }
        createAt = now;
        updateAt = now;
    }

    @PreUpdate
    public void perUpdate(){
        updateAt = LocalDateTime.now();
    }
}
