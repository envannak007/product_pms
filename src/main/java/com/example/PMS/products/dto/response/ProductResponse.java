package com.example.PMS.products.dto.response;


import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
        Integer id,
         String name,
         String description,
         BigDecimal price,
         Integer stock,
         String gtin,
         Integer categoryId,
         String categoryName,
        List<String> images
) {

}
