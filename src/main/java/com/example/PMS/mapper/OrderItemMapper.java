package com.example.PMS.mapper;

import com.example.PMS.dto.response.OrderItemResponse;
import com.example.PMS.entity.OrderItems;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public OrderItemResponse toResponse(OrderItems orderItems){
        return new OrderItemResponse(
                orderItems.getId(),
                orderItems.getQuantity(),
                orderItems.getOrder().getId(),
                orderItems.getProduct().getId(),
                orderItems.getPrice()
        );
    }
}
