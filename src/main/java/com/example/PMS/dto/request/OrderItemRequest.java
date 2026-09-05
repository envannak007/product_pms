package com.example.PMS.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderItemRequest (

        @NotNull(message = "quantity is required.")
        @Min(value = 1,message = "Quantity must be greater than 0")
        Integer quantity,

        @NotNull(message = "Order id is required.")
        Integer orderId,
        @NotNull(message = "Product id is required.")
        Integer productId,

        @NotNull(message = "Price is required.")
        @Min(value = 0,message = "Price must not be negative")
        BigDecimal price
){
}
