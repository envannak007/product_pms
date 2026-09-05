package com.example.PMS.mapper;
import com.example.PMS.dto.response.OrderResponse;
import com.example.PMS.entity.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponse toResponse(OrderEntity order){
        return new OrderResponse(
                order.getId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getUser().getId(),
                order.getCreateAt(),
                order.getUpdateAt()
        );

    }
}
