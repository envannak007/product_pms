package com.example.PMS.orders.repository;

import com.example.PMS.orders.entitty.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {

}
