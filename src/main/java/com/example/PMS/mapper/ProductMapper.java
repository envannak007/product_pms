package com.example.PMS.mapper;

import com.example.PMS.dto.request.ProductRequest;
import com.example.PMS.dto.response.ProductImageResponse;
import com.example.PMS.dto.response.ProductResponse;
import com.example.PMS.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductEntity toEntity(ProductRequest request){

        ProductEntity productEntity = new ProductEntity();

        productEntity.setName(request.name());
        productEntity.setDescription(request.description());
        productEntity.setPrice(request.price());
        productEntity.setStock(request.stock());

        return productEntity;
    }
    public ProductResponse toResponse(ProductEntity productEntity){
        return new ProductResponse(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getPrice(),
                productEntity.getStock(),
                productEntity.getCategory().getId(),
                productEntity.getCategory().getName(),
                productEntity.getImage()
                        .stream()
                        .map(images -> new ProductImageResponse(
                                images.getId(),
                                images.getImageUrl(),
                                images.getPublicId(),
                                productEntity.getId()
                        ))
                        .toList()
        );
    }

    public void updateEntity(
            ProductEntity productEntity,
            ProductRequest request
    ){
        productEntity.setName(request.name());
        productEntity.setDescription(request.description());
        productEntity.setPrice(request.price());
        productEntity.setStock(request.stock());
    }
}
