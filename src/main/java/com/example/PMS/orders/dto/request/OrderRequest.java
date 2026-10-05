package com.example.PMS.orders.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(

        @NotNull(message = "User ID is required")
        Integer userId,

        @NotEmpty(message = "Order items are required")
        @Valid
        List<OrderItemRequest> item
) {
}
