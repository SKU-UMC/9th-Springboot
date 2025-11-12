package com.umc.springboot.domain.review.service;

import com.umc.springboot.domain.review.converter.ReviewConverter;
import com.umc.springboot.domain.review.dto.request.ReviewRequest;
import com.umc.springboot.domain.review.dto.response.ReviewResponse;
import com.umc.springboot.domain.review.entity.Review;
import com.umc.springboot.domain.review.entity.ReviewImage;
import com.umc.springboot.domain.review.repository.ReviewImageRepository;
import com.umc.springboot.domain.review.repository.ReviewRepository;
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
  private final ReviewImageRepository reviewImageRepository; // 선택(일괄 조회 전략)

  public Page<ReviewResponse> getReviewsByUser(Long userId, ReviewRequest filter,
      Pageable pageable) {
    Page<Review> page = reviewRepository.findMyReviews(userId, filter, pageable);

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
