package com.example.PMS.carts.dto.response;

import java.math.BigDecimal;

public record CartItemResponse(
        Integer id,
        Integer productId,
        String productName,
        Integer quantity,
        BigDecimal price,
        BigDecimal subTotal
) {
}
