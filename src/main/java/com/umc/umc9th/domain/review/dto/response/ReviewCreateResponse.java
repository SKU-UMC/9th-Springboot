package com.umc.umc9th.domain.review.dto.response;

import com.umc.umc9th.domain.review.entity.Review;

public record ReviewCreateResponse(
        Long reviewId,
        Long storeId,
        Long userId,
        String reviewText,
        Double reviewRating
) {

    public static ReviewCreateResponse from(Review review) {
        return new ReviewCreateResponse(
                review.getId(),
                review.getStore().getId(),
                review.getUser().getId(),
                review.getReviewText(),
                review.getReviewRating()
        );
    }
}