package com.example.PMS.carts.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CartResponse(
        Integer id,
        Integer userId,
        List<CartItemResponse> cartItems,
        Integer totalItem,
        BigDecimal totalAmount,
        LocalDateTime createAt,
        LocalDateTime updateAt
) {
}
