package com.umc.springboot.domain.review.controller;

import com.umc.springboot.domain.review.dto.request.ReviewCreateRequest;
import com.umc.springboot.domain.review.dto.request.ReviewRequest;
import com.umc.springboot.domain.review.dto.response.PageableResponse;
import com.umc.springboot.domain.review.dto.response.ReviewResponse;
import com.umc.springboot.domain.review.service.ReviewService;
import com.umc.springboot.global.response.BaseResponse;
import com.umc.springboot.global.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Validated
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
   * 내 리뷰 페이징 조회
   */
  @Operation(
      summary = "내 리뷰 페이징 조회",
      description = """
          현재 로그인 사용자의 리뷰를 페이지 단위로 조회합니다.
          - page: 1 이상의 정수(1 페이지부터 시작)
          - size: 10 고정
          - 필터: storeName(정확 일치), ratingBand(1~5)
          """
  )
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BaseResponse<PageableResponse<ReviewResponse>>> getMyReviewsPaged(
      @RequestParam(required = false) Long storeId,
      @RequestParam(name = "page", defaultValue = "1") Integer page
  ) {
    Long userId = SecurityUtil.getCurrentUserId();

    int pageIndex = page - 1;

    Pageable pageable = PageRequest.of(
        pageIndex,
        10,
        Sort.by(Sort.Direction.DESC, "createdAt")
    );

    ReviewRequest filter = ReviewRequest.builder()
        .storeId(storeId)
        .build();

    Page<ReviewResponse> result = reviewService.getReviewsByUser(userId, filter, pageable);

    PageableResponse<ReviewResponse> body = PageableResponse.from(result);

    return ResponseEntity.ok(
        BaseResponse.success("내 리뷰 페이징 조회에 성공했습니다.", body)
    );
  }


  /**
   * 가게 리뷰 페이징 조회
   */
  @Operation(
      summary = "가게 리뷰 목록 조회",
      description = """
          특정 가게에 대한 리뷰를 페이지 단위로 조회합니다.
          - page: 1 이상의 정수(1 페이지부터 시작)
          - size: 10으로 고정
          - 정렬: 작성일시(createdAt) 내림차순
          """
  )
  @GetMapping(value = "/stores/{storeId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BaseResponse<PageableResponse<ReviewResponse>>> getStoreReviews(
      @PathVariable Long storeId,
      @RequestParam(name = "page", defaultValue = "1") Integer page
  ) {
    int pageIndex = page - 1;

    Pageable pageable = PageRequest.of(
        pageIndex,
        10,
        Sort.by(Sort.Direction.DESC, "createdAt")
    );

    Page<ReviewResponse> result = reviewService.getReviewsByStore(storeId, pageable);

    PageableResponse<ReviewResponse> body = PageableResponse.from(result);

    return ResponseEntity.ok(
        BaseResponse.success("가게 리뷰 조회에 성공했습니다.", body)
    );
  }
}
