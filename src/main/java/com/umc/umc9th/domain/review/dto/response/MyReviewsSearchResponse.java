package com.umc.umc9th.domain.review.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record MyReviewsSearchResponse(
        Long reviewId,
        Long storeId,
        String storeName,
        Double rating,
        String content,
        LocalDateTime createdAt,
        List<String> imageUrls,   // 리뷰 이미지 URL 목록
        List<String> comments     // 리뷰 댓글 내용 목록
) {}

