package com.example.PMS.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank(message = "Name must not be black")
        @Size(min = 2,max = 100,message = "Name must be between 2 to 100 characters")
        String name,

        @Size(max = 1000,message = "Description must to be less than 1000")
        String description
) {
}
