package com.umc.umc9th.domain.review.controller;

import com.umc.umc9th.domain.review.dto.request.MyReviewsSearchRequest;
import com.umc.umc9th.domain.review.dto.request.ReviewCreateRequest;
import com.umc.umc9th.domain.review.dto.response.MyReviewsSearchResponse;
import com.umc.umc9th.domain.review.dto.response.ReviewCreateResponse;
import com.umc.umc9th.domain.review.service.ReviewService;
import com.umc.umc9th.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 내가 작성한 리뷰 보기 (가게별 / 별점별)
     * - ratingFilter: 0 전체 / 1~5 해당 점수대
     * - storeId: 선택
     * - page, size, sort 쿼리 파라미터로 페이징
     *
     * +++ jwt토큰 학습 후에는 토큰에 userId 넣어서 불필요한 리퀘스트dto 없애는 방향으로 리팩토링해보기
     * ? 필드 하나짜리의 dto는 불필요해보임 but, url에서 userId 같은 필드 정보는 숨겨주는 것을 지향
     */
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

    @PostMapping("/{storeId}/reviews")
    public ApiResponse<ReviewCreateResponse> createReview(
            @PathVariable Long storeId,
            @RequestBody @Valid ReviewCreateRequest request
    ) {
        ReviewCreateResponse response =
                reviewService.createReview(storeId, request.userId(), request);

        return ApiResponse.success(response);
    }

}