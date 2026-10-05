package com.example.PMS.rating.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRatingRequest(
        @Min(1)
        @Max(5)
        Integer rating,

        @NotBlank(message = "Comment is required.")
        String comment,

        @NotNull(message = "Product id is required.")
        Integer productId
) {
}
