package com.umc.springboot.domain.review.service;

import com.umc.springboot.domain.review.converter.ReviewConverter;
import com.umc.springboot.domain.review.dto.request.ReviewCreateRequest;
import com.umc.springboot.domain.review.dto.request.ReviewRequest;
import com.umc.springboot.domain.review.dto.response.ReviewResponse;
import com.umc.springboot.domain.review.entity.Review;
import com.umc.springboot.domain.review.entity.ReviewImage;
import com.umc.springboot.domain.review.exception.ReviewErrorCode;
import com.umc.springboot.domain.review.repository.ReviewImageRepository;
import com.umc.springboot.domain.review.repository.ReviewRepository;
import com.umc.springboot.domain.store.entity.Store;
import com.umc.springboot.domain.store.repository.StoreRepository;
import com.umc.springboot.domain.user.entity.User;
import com.umc.springboot.domain.user.repository.UserRepository;
import com.umc.springboot.global.exception.CustomException;
import com.umc.springboot.global.exception.GlobalErrorCode;
import java.math.BigDecimal;
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
  private final StoreRepository storeRepository;
  private final UserRepository userRepository;

  /**
   * 가게에 리뷰 생성
   */
  @Transactional
  public ReviewResponse createReview(Long userId, Long storeId, ReviewCreateRequest req) {

    // 유저 조회
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));

    // 가게 조회
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));

    // 중복 리뷰 체크
    if (reviewRepository.existsByUserIdAndStoreId(userId, storeId)) {
      throw new CustomException(ReviewErrorCode.DUPLICATE_REVIEW);
    }

    // 별점 방어 검증
    BigDecimal rating = req.getRating();
    if (rating.compareTo(new BigDecimal("0.5")) < 0
        || rating.compareTo(new BigDecimal("5.0")) > 0) {
      throw new CustomException(ReviewErrorCode.INVALID_REVIEW_RATING);
    }

    // 리뷰 저장
    Review review = Review.builder()
        .user(user)
        .store(store)
        .title(req.getTitle())
        .description(req.getDescription())
        .rating(rating)
        .build();

    reviewRepository.save(review);

    // 이미지 저장
    List<String> imageUrls = req.getImageUrls() != null ? req.getImageUrls()
        : Collections.emptyList();

    if (!imageUrls.isEmpty()) {
      List<ReviewImage> images = imageUrls.stream()
          .map(url -> ReviewImage.builder()
              .review(review)
              .imageUrl(url)
              .build())
          .toList();

      reviewImageRepository.saveAll(images);
      review.getImages().addAll(images);
    }

    return ReviewConverter.toResponse(review, imageUrls);
  }

  /**
   * 내 리뷰 목록 조회 (기존 코드 유지)
   */
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

    // 이하 기존 코드 그대로
    List<Long> reviewIds = page.getContent().stream().map(Review::getId).toList();

    Map<Long, List<String>> imagesByReviewId = Collections.emptyMap();

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
