package com.umc.umc9th.domain.review.controller;

import com.umc.umc9th.domain.review.dto.request.MyReviewsSearchRequest;
import com.umc.umc9th.domain.review.dto.request.ReviewCreateRequest;
import com.umc.umc9th.domain.review.dto.response.MyReviewsSearchResponse;
import com.umc.umc9th.domain.review.dto.response.ReviewCreateResponse;
import com.umc.umc9th.domain.review.service.ReviewService;
import com.umc.umc9th.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Review", description = "리뷰 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;


    // 9주차 내가 작성한 리뷰 목록
    /**
     * 내가 작성한 리뷰 보기 (가게별 / 별점별)
     * - ratingFilter: 0 전체 / 1~5 해당 점수대
     * - storeId: 선택
     * - page, size, sort 쿼리 파라미터로 페이징
     *
     * +++ jwt토큰 학습 후에는 토큰에 userId 넣어서 불필요한 리퀘스트dto 없애는 방향으로 리팩토링해보기
     * ? 필드 하나짜리의 dto는 불필요해보임 but, url에서 userId 같은 필드 정보는 숨겨주는 것을 지향
     */

    @Operation(
            summary = "내 리뷰 조회",
            description = "유저가 작성한 리뷰 목록을 조회합니다. 가게별, 별점별, 최신순 정렬을 지원합니다."
    )
    @GetMapping
    public ApiResponse<Page<MyReviewsSearchResponse>> getMyReviews(
            @RequestBody MyReviewsSearchRequest body,
            @RequestParam(required = false) Long storeId,
            @RequestParam(defaultValue = "0") Integer ratingFilter,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<MyReviewsSearchResponse> page =
                reviewService.getMyReviews(body.userId(), storeId, ratingFilter, pageable);

        return ApiResponse.success(page);
    }

    @Operation(
            summary = "리뷰 작성",
            description = "특정 가게(storeId)에 대한 리뷰를 등록합니다. 리뷰텍스트와 별점을 함께 요청으로 전달해야 합니다."
    )
    @PostMapping("/stores/{storeId}")
    public ApiResponse<ReviewCreateResponse> createReview(
            @PathVariable Long storeId,
            @RequestBody @Valid ReviewCreateRequest request
    ) {
        ReviewCreateResponse response =
                reviewService.createReview(storeId, request.userId(), request);

        return ApiResponse.success(response);
    }
}