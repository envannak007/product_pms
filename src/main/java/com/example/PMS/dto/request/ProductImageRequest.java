package com.example.PMS.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record ProductImageRequest(

        @NotNull(message = "Product id is required")
        Integer productID,

        @NotNull(message = "Image is required")
        MultipartFile image
) {
}
