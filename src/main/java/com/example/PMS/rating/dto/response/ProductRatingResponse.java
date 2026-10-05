package com.example.PMS.rating.dto.response;

import java.time.LocalDateTime;

public record ProductRatingResponse(
        Integer id,
        Integer rating,
        String comment,
        Integer userId,
        String userName,
        Integer productId,
        LocalDateTime createAt,
        LocalDateTime updateAt
) {
}
