package com.example.PMS.orders.dto.response;


import com.example.PMS.orders.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public record OrderResponse(
        Integer id,
        LocalDateTime orderDate,
        Integer userId,
        BigDecimal totaAmount,
        OrderStatus status,
        List<OrderItemResponse> items
) {}
