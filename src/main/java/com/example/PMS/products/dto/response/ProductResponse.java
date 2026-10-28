package com.example.PMS.products.dto.response;


import com.example.PMS.products.enums.DiscountType;

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
        DiscountType discountType,
        BigDecimal discountValue,
        BigDecimal discountAmount,
        BigDecimal finalPrice,
        List<String> images
) {

}
