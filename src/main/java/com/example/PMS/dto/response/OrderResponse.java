package com.example.PMS.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
        Integer id,
        LocalDateTime orderDate,
        BigDecimal totalAmount,
        String status,
        Integer userId,
        LocalDateTime createAt,
        LocalDateTime updateAt
) {
}
