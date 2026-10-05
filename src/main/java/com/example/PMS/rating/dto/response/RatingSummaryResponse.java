package com.example.PMS.rating.dto.response;

public record RatingSummaryResponse(
        Double averageRating,
        Long totalRatings,
        Long fiveStar,
        Long fourStar,
        Long threeStar,
        Long twoStar,
        Long oneStar
) {
}