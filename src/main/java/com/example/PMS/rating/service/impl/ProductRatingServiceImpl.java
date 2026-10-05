package com.example.PMS.rating.service.impl;

import com.example.PMS.rating.dto.request.ProductRatingRequest;
import com.example.PMS.rating.dto.response.ProductRatingResponse;
import com.example.PMS.rating.dto.response.RatingSummaryResponse;
import com.example.PMS.products.entity.ProductEntity;
import com.example.PMS.rating.entitty.ProductRatingEntity;
import com.example.PMS.users.entitty.UserEntity;
import com.example.PMS.common.exception.ResourceNotFoundException;
import com.example.PMS.rating.mapper.ProductRatingMapper;
import com.example.PMS.rating.repository.ProductRatingRepository;
import com.example.PMS.products.repository.ProductRepository;
import com.example.PMS.users.repository.UserRepository;
import com.example.PMS.rating.service.ProductRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductRatingServiceImpl implements ProductRatingService {
    private final ProductRatingRepository productRatingRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductRatingMapper productRatingMapper;

    @Override
    public ProductRatingResponse createRating(Integer userId, ProductRatingRequest request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not found with id : " + userId));

        ProductEntity product = productRepository.findById(request.productId())
                .orElseThrow(()->new ResourceNotFoundException("Product not found with id : "+ request.productId()));

        ProductRatingEntity productRating = productRatingRepository.findByUserIdAndProductId(userId,request.productId())
                .orElse(null);

        if (productRating != null){
            productRating.setRating(request.rating());
            productRating.setComment(request.comment());
            productRating.setUpdateAt(LocalDateTime.now());
        }else {
            productRating = new ProductRatingEntity();
            productRating.setRating(request.rating());
            productRating.setComment(request.comment());
            productRating.setUser(user);
            productRating.setProduct(product);
            productRating.setCreateAt(LocalDateTime.now());
            productRating.setUpdateAt(LocalDateTime.now());
        }

        ProductRatingEntity saved = productRatingRepository.save(productRating);

        return productRatingMapper.toProductRatingResponse(saved);
    }

    @Override
    public RatingSummaryResponse getRatingSummary(Integer productId) {
        if (!productRepository.existsById(productId)){
            throw new ResourceNotFoundException("Product not found.");
        }

        Double averageRating = productRatingRepository.getAverageRating(productId);
        List<Object[]> ratingCount = productRatingRepository.getRatingCounts(productId);
        long fiveStar = 0;
        long fourStar = 0;
        long threeStar = 0;
        long twoStar = 0;
        long oneStar = 0;

        for (Object[] row : ratingCount){
            Integer rating = (Integer) row[0];
            Long count = (Long) row[1];

            switch (rating){
                case 5-> fiveStar = count;
                case 4-> fourStar = count;
                case 3->threeStar = count;
                case 2->twoStar = count;
                case 1->oneStar = count;
            }
        }
        long totalRatings = fiveStar + fourStar + threeStar + twoStar + oneStar;
        return new RatingSummaryResponse(
                averageRating,
                totalRatings,
                fiveStar,
                fourStar,
                threeStar,
                twoStar,
                oneStar
        );
    }

    @Override
    public List<ProductRatingResponse> getProductRatings(
            Integer productId
    ) {

        List<ProductRatingEntity> ratings =
                productRatingRepository
                        .findByProductIdOrderByCreateAtDesc(productId);

        return ratings.stream()
                .map(productRatingMapper::toProductRatingResponse)
                .toList();
    }
}
