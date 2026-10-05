package com.example.PMS.carts.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddToCartRequest(

        @NotNull(message = "Product id is required.")
        Integer productId,

        @NotNull(message = "Quantity is required.")
        @Min(1)
        Integer quantity
) {
}
