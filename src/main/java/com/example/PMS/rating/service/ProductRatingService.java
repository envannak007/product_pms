package com.example.PMS.rating.service;

import com.example.PMS.rating.dto.request.ProductRatingRequest;
import com.example.PMS.rating.dto.response.ProductRatingResponse;
import com.example.PMS.rating.dto.response.RatingSummaryResponse;

import java.util.List;

public interface ProductRatingService {
    ProductRatingResponse createRating (Integer userId, ProductRatingRequest request);
    RatingSummaryResponse  getRatingSummary(Integer productId);
    List<ProductRatingResponse> getProductRatings(
            Integer productId
    );
}
