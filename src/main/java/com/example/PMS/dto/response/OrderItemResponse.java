package com.example.PMS.dto.response;

import java.math.BigDecimal;

public record OrderItemResponse(
        Integer id,
        Integer orderId,
        Integer productId,
        Integer quantity,
        BigDecimal price
) {
}
