package com.example.PMS.dto.response;

import com.example.PMS.entity.ProductImageEntity;


import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
        Integer id,
         String name,
         String description,
         BigDecimal price,
         Integer stock,

         Integer categoryId,
         String categoryName,
        List<ProductImageResponse> image
) {

}
