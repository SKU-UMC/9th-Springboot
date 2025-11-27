package com.umc.springboot.domain.review.controller;

import com.umc.springboot.domain.review.dto.request.ReviewCreateRequest;
import com.umc.springboot.domain.review.dto.request.ReviewRequest;
import com.umc.springboot.domain.review.dto.response.ReviewResponse;
import com.umc.springboot.domain.review.service.ReviewService;
import com.umc.springboot.global.response.BaseResponse;
import com.umc.springboot.global.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
@Tag(name = "Review", description = "리뷰 조회/작성 API")
public class ReviewController {

  private final ReviewService reviewService;

  /**
   * 가게에 리뷰 작성
   */
  @Operation(summary = "가게에 리뷰 작성")
  @PostMapping(
      value = "/stores/{storeId}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<BaseResponse<ReviewResponse>> createReview(
      @PathVariable Long storeId,
      @Valid @RequestBody ReviewCreateRequest request
  ) {
    Long userId = SecurityUtil.getCurrentUserId();
    ReviewResponse response = reviewService.createReview(userId, storeId, request);
    return ResponseEntity.ok(BaseResponse.success("리뷰 작성에 성공했습니다.", response));
  }

  /**
   * 내 리뷰 목록 조회
   */
  @Operation(
      summary = "내 리뷰 목록 조회",
      description = """
          현재 로그인 사용자의 리뷰 목록을 조회합니다.
          - 필터: storeName(정확 일치), ratingBand(1~5)
          - ratingBand=4 ⇒ [4.0,5.0), ratingBand=5 ⇒ 5.0만
          """)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BaseResponse<Page<ReviewResponse>>> getReviews(
      @RequestParam(required = false) String storeName,
      @RequestParam(required = false) @Min(1) @Max(5) Integer ratingBand,
      @PageableDefault(size = 10, sort = "createdAt,desc") Pageable pageable
  ) {
    Long userId = SecurityUtil.getCurrentUserId();

    ReviewRequest filter = ReviewRequest.builder()
        .storeName(storeName)
        .ratingBand(ratingBand)
        .build();

    Page<ReviewResponse> page = reviewService.getReviewsByUser(userId, filter, pageable);
    return ResponseEntity.ok(BaseResponse.success("리뷰 조회에 성공했습니다.", page));
  }
}
