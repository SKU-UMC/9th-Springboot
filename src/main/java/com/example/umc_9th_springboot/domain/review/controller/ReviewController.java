package com.example.umc_9th_springboot.domain.review.controller;

import com.example.umc_9th_springboot.domain.review.dto.ReviewResponse;
import com.example.umc_9th_springboot.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * 리뷰 보기 API (가게명 + 별점대 필터)
 * 요청:
 *  - /api/reviews?shop=반이학생마라탕마라반
 *  - /api/reviews?score=4
 *  - /api/reviews?shop=반이학생마라탕마라반&score=5
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public Page<ReviewResponse> getReviews(
            @RequestParam(required = false) String shop,
            @RequestParam(required = false, name = "score") Integer score,
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return reviewService.getReviews(shop, score, pageable);
    }
}
