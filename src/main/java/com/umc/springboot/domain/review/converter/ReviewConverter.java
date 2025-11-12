package com.umc.springboot.domain.review.converter;

import com.umc.springboot.domain.review.dto.response.ReviewResponse;
import com.umc.springboot.domain.review.entity.Review;
import com.umc.springboot.domain.review.entity.ReviewImage;
import java.util.List;

public class ReviewConverter {

  public static ReviewResponse toResponse(Review review) {
    List<String> imageUrls = review.getImages().stream()
        .map(ReviewImage::getImageUrl)
        .toList();

    return base(review, imageUrls);
  }

  // 이미지 URL 목록을 외부에서 주입하는 경우
  public static ReviewResponse toResponse(Review review, List<String> imageUrls) {
    return base(review, imageUrls);
  }

  private static ReviewResponse base(Review review, List<String> imageUrls) {
    return ReviewResponse.builder()
        .reviewId(review.getId())
        .storeId(review.getStore().getId())
        .storeName(review.getStore().getName())
        .title(review.getTitle())
        .description(review.getDescription())
        .rating(review.getRating())
        .imageUrls(imageUrls)
        .createdAt(review.getCreatedAt())
        .build();
  }
}
