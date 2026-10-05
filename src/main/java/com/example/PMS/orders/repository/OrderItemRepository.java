package com.example.PMS.orders.repository;

import com.example.PMS.orders.entitty.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity,String> {
    List<OrderItemEntity> findByOrderId(Integer orderId);
}
