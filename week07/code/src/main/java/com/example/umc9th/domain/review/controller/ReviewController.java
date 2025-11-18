package com.example.umc9th.domain.review.controller;

import com.example.umc9th.domain.review.converter.ReviewConverter;
import com.example.umc9th.domain.review.dto.res.ReviewResDTO;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.service.ReviewQueryService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewQueryService reviewQueryService;

    @GetMapping("/reviews/search")  // 리뷰 검색
    public ApiResponse<List<ReviewResDTO.Search>> searchReview(@RequestParam String query, @RequestParam String type) {
        List<Review> result = reviewQueryService.searchReview(query, type);

        List<ReviewResDTO.Search> resultDTO = result.stream().map(ReviewConverter::toSearchDTO).toList();
        GeneralSuccessCode code = GeneralSuccessCode.OK;

        return ApiResponse.onSuccess(code, resultDTO);
    }

    @GetMapping("/reviews/members/{memberId}")  // 회원 리뷰 검색
    public ApiResponse<List<ReviewResDTO.Search>> memberReviews(@PathVariable String memberId, @RequestParam String query, @RequestParam String type) {
        List<Review> result = reviewQueryService.memberReviews(Long.parseLong(memberId), query, type);

        List<ReviewResDTO.Search> resultDTO = result.stream().map(ReviewConverter::toSearchDTO).toList();
        GeneralSuccessCode code = GeneralSuccessCode.OK;

        return ApiResponse.onSuccess(code, resultDTO);

    }
}
