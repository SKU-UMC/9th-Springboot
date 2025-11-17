package com.umc.springboot.domain.review.service;

import com.umc.springboot.domain.review.converter.ReviewConverter;
import com.umc.springboot.domain.review.dto.request.ReviewRequest;
import com.umc.springboot.domain.review.dto.response.ReviewResponse;
import com.umc.springboot.domain.review.entity.Review;
import com.umc.springboot.domain.review.entity.ReviewImage;
import com.umc.springboot.domain.review.repository.ReviewImageRepository;
import com.umc.springboot.domain.review.repository.ReviewRepository;
import com.umc.springboot.global.exception.CustomException;
import com.umc.springboot.global.exception.GlobalErrorCode;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final ReviewImageRepository reviewImageRepository;

  public Page<ReviewResponse> getReviewsByUser(Long userId, ReviewRequest filter,
      Pageable pageable) {

    // ratingBand 검증
    if (filter.getRatingBand() != null) {
      int band = filter.getRatingBand();
      if (band < 1 || band > 5) {
        throw new CustomException(GlobalErrorCode.INVALID_INPUT_VALUE);
      }
    }

    Page<Review> page = reviewRepository.findMyReviews(userId, filter, pageable);

    // 리뷰 ID 추출
    List<Long> reviewIds = page.getContent().stream().map(Review::getId).toList();

    Map<Long, List<String>> imagesByReviewId = Collections.emptyMap();

    // 이미지 일괄 조회
    if (!reviewIds.isEmpty()) {
      List<ReviewImage> allImages = reviewImageRepository.findByReviewIdIn(reviewIds);

      imagesByReviewId = allImages.stream()
          .collect(Collectors.groupingBy(
              img -> img.getReview().getId(),
              Collectors.mapping(ReviewImage::getImageUrl, Collectors.toList())
          ));
    }

    Map<Long, List<String>> finalImagesMap = imagesByReviewId;

    List<ReviewResponse> content = page.getContent().stream()
        .map(r -> ReviewConverter.toResponse(
            r,
            finalImagesMap.getOrDefault(r.getId(), List.of())
        ))
        .toList();

    return new PageImpl<>(content, pageable, page.getTotalElements());
  }
}
