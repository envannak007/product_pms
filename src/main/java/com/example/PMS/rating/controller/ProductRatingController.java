package com.example.PMS.rating.controller;

import com.example.PMS.rating.dto.request.ProductRatingRequest;
import com.example.PMS.common.response.BaseResponse;
import com.example.PMS.rating.dto.response.ProductRatingResponse;
import com.example.PMS.rating.dto.response.RatingSummaryResponse;
import com.example.PMS.rating.service.ProductRatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-ratings")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProductRatingController {

    private final ProductRatingService productRatingService;

    // Create / Update Rating
    @PostMapping("/user/{userId}")
    public ResponseEntity<BaseResponse<ProductRatingResponse>> createRating(
            @PathVariable Integer userId,
            @Valid @RequestBody ProductRatingRequest request
    ) {

        ProductRatingResponse response =
                productRatingService.createRating(
                        userId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        BaseResponse.created("Rating successfully.",response)
                );
    }

    // Get Product Rating Summary
    @GetMapping("/product/{productId}/summary")
    public ResponseEntity<BaseResponse<RatingSummaryResponse>> getRatingSummary(
            @PathVariable Integer productId
    ) {

        RatingSummaryResponse response =
                productRatingService.getRatingSummary(
                        productId
                );

        return ResponseEntity.ok(
                BaseResponse.ok("successfully",response)
        );
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<BaseResponse<List<ProductRatingResponse>>> getProductRatings(
            @PathVariable Integer productId
    ) {

        List<ProductRatingResponse> response =
                productRatingService.getProductRatings(productId);

        return ResponseEntity.ok(
                BaseResponse.ok(
                        "Successfully",
                        response
                )
        );
    }
}