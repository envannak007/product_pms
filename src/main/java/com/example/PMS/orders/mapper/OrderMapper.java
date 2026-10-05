package com.example.PMS.orders.mapper;

import com.example.PMS.orders.dto.response.OrderItemResponse;
import com.example.PMS.orders.dto.response.OrderResponse;
import com.example.PMS.orders.entitty.OrderEntity;
import com.example.PMS.orders.entitty.OrderItemEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderMapper {

    public OrderResponse toResponse (OrderEntity order){
        return new OrderResponse(
                order.getId(),
                order.getOrderDate(),
                order.getUser() != null ? order.getUser().getId() : null,
                order.getTotalAmount(),
                order.getStatus(),
                order.getOrderItem()
                        .stream()
                        .map(this::toItemResponse)
                        .toList()
        );
    }

    public OrderItemResponse toItemResponse(OrderItemEntity orderItem){
        BigDecimal subtotal = orderItem.getPrice()
                .multiply(BigDecimal.valueOf(orderItem.getQuantity()));

        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                subtotal
        );
    }
}
