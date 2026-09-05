package com.example.PMS.mapper;

import com.example.PMS.dto.response.ProductImageResponse;
import com.example.PMS.entity.ProductImageEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductImageMapper {

    public ProductImageResponse toResponse(ProductImageEntity image){
        return new ProductImageResponse(
                image.getId(),
                image.getImageUrl(),
                image.getPublicId(),
                image.getProduct().getId()
        );
    }
}
