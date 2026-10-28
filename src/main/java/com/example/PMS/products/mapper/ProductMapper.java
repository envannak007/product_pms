package com.example.PMS.products.mapper;

import com.example.PMS.products.dto.request.ProductRequest;
import com.example.PMS.products.dto.response.ProductResponse;
import com.example.PMS.products.entity.ProductEntity;
import com.example.PMS.products.entity.ProductImageEntity;
import com.example.PMS.products.enums.DiscountType;
import com.example.PMS.products.service.ProductDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final ProductDiscountService productDiscountService;
    public ProductEntity toEntity(ProductRequest request){

        ProductEntity productEntity = new ProductEntity();

        productEntity.setName(request.name());
        productEntity.setDescription(request.description());
        productEntity.setPrice(request.price());
        productEntity.setStock(request.stock());
        productEntity.setDiscountValue(request.discountValue() != null ? request.discountValue() : BigDecimal.ZERO);
        productEntity.setDiscountType(request.discountType() != null ? request.discountType() : DiscountType.NONE);

        return productEntity;
    }
    public ProductResponse toResponse(ProductEntity productEntity){

        return new ProductResponse(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getPrice(),
                productEntity.getStock(),
                productEntity.getGtin(),
                productEntity.getCategory().getId(),
                productEntity.getCategory().getName(),
                productEntity.getDiscountType(),
                productEntity.getDiscountValue(),
                productDiscountService.calculateDiscountAmount(productEntity),
                productDiscountService.calculateFinalPrice(productEntity),
                productEntity.getImage()
                        .stream()
                        .map(ProductImageEntity::getImageUrl)
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
        productEntity.setDiscountValue(request.discountValue() != null ? request.discountValue() : BigDecimal.ZERO);
        productEntity.setDiscountType(request.discountType() != null ? request.discountType() : DiscountType.NONE);
    }
}
