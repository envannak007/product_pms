package com.example.PMS.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OtherItemRequest(

        @NotNull(message = "Quantity is required")
        Integer quantity,

        BigDecimal price,

        @NotNull(message = "Product is required")
        Integer productId

) {
}
