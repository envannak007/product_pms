package com.example.PMS.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "Name must not be black")
        @Size(min = 2,max = 100,message = "Name must be between 2 to 100 characters")
        String name,

        @Size(max = 1000,message = "Description must to be less than 1000")
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(
                value = "0.0",
                inclusive = false,
                message = "Price must be greater than 0"
        )
        BigDecimal price,

        @NotNull(message = "Stock is required")
        @Min(
                value = 0,
                message = "Stock cannot be negative"
        )
        Integer stock,

        @NotNull(message = "Category is required")
        Integer categoryId
) {
}
