package com.example.umc9th.domain.review.controller;

import com.example.umc9th.domain.review.converter.ReviewConverter;
import com.example.umc9th.domain.review.dto.req.ReviewReqDTO;
import com.example.umc9th.domain.review.dto.res.ReviewResDTO;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc9th.domain.review.service.ReviewQueryService;
import com.example.umc9th.domain.review.service.ReviewService;
import com.example.umc9th.global.annotation.ValidPage;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc9th.global.dto.PageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewQueryService reviewQueryService;
    private final ReviewService reviewService;

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

    // 리뷰 추가 API
    @PostMapping("/reviews")
    public ApiResponse<ReviewResDTO.AddDTO> addReview(@RequestBody ReviewReqDTO.AddDTO dto) {
        ReviewResDTO.AddDTO result = reviewService.addReview(dto);

        ReviewSuccessCode code = ReviewSuccessCode.OK;
        return ApiResponse.onSuccess(code, result);
    }

    // 가게의 리뷰 목록 조회
    @Operation(
            summary = "가게의 리뷰 목록 조회 API By 마크 (개발 중)",
            description = "특정 가게의 리뷰를 모두 조회합니다. 페이지네이션으로 제공합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/reviews")
    public ApiResponse<PageDTO<ReviewResDTO.ReviewPreViewDTO>> getReviews(@RequestParam String storeName, @Valid @ValidPage Pageable pageable) {

        ReviewSuccessCode code = ReviewSuccessCode.FOUND;
        return ApiResponse.onSuccess(code, reviewService.findReview(storeName, pageable));
    }

    // 내가 작성한 리뷰 목록 (11.25)
    @Operation(
            summary = "내가 작성한 리뷰 목록 조회 API (11.25)",
            description = "내가 작성한 리뷰를 모두 조회합니다. 페이지네이션으로 제공합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/reviews/members/{memberId}/me")
    public ApiResponse<PageDTO<ReviewResDTO.ReviewPreViewDTO>> getMyReviews(@PathVariable Long memberId, @Valid @ValidPage Pageable pageable) {
        PageDTO<ReviewResDTO.ReviewPreViewDTO> result = reviewService.getMyReviews(memberId, pageable);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, result);
    }






}
