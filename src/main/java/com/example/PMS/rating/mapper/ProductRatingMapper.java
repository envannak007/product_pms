package com.example.PMS.rating.mapper;

import com.example.PMS.rating.dto.response.ProductRatingResponse;
import com.example.PMS.rating.entitty.ProductRatingEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductRatingMapper {

    public ProductRatingResponse toProductRatingResponse(ProductRatingEntity productRating){
        return new ProductRatingResponse(
                productRating.getId(),
                productRating.getRating(),
                productRating.getComment(),
                productRating.getUser().getId(),
                productRating.getUser().getUsername(),
                productRating.getProduct().getId(),
                productRating.getCreateAt(),
                productRating.getUpdateAt()
        );
    }
}
