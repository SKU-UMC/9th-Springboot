package com.example.umc_9th_springboot.domain.review.controller;

import com.example.umc_9th_springboot.domain.review.dto.ReviewResponse;
import com.example.umc_9th_springboot.domain.review.dto.req.ReviewReqDTO;
import com.example.umc_9th_springboot.domain.review.dto.res.ReviewResDTO;
import com.example.umc_9th_springboot.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc_9th_springboot.domain.review.service.ReviewService;
import com.example.umc_9th_springboot.global.apiPayload.ApiResponse;
import com.example.umc_9th_springboot.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc_9th_springboot.global.validation.ValidPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Review", description = "리뷰 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;


     //리뷰 목록 조회 (가게명 + 별점 필터)
    @Operation(
            summary = "리뷰 목록 조회 API",
            description = "가게명(shop)과 별점(score)을 기준으로 리뷰 목록을 10개씩 페이징 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "리뷰 목록 조회 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 파라미터 (예: score 범위 오류)"
            )
    })
    @GetMapping
    public ApiResponse<Page<ReviewResponse>> getReviews(
            @RequestParam(required = false) String shop,
            @RequestParam(required = false, name = "score") Integer score,
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        Page<ReviewResponse> reviewPage = reviewService.getReviews(shop, score, pageable);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, reviewPage);
    }


     //리뷰 작성
    @Operation(
            summary = "리뷰 작성 API",
            description = "요청 바디로 전달된 정보를 기반으로 새로운 리뷰를 등록합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "리뷰 작성 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "요청 바디 검증 실패"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "사용자 또는 가게를 찾을 수 없음"
            )
    })
    @PostMapping
    public ApiResponse<ReviewResDTO.CreateDTO> createReview(
            @Valid @RequestBody ReviewReqDTO.CreateDTO dto
    ) {
        return ApiResponse.onSuccess(
                ReviewSuccessCode.CREATED,
                reviewService.createReview(dto)
        );
    }

     //내가 작성한 리뷰 목록 조회
    @Operation(
            summary = "내가 작성한 리뷰 목록 조회 API",
            description = "userId 기준으로 내가 작성한 리뷰를 10개씩 페이징하여 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "내가 작성한 리뷰 목록 조회 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "page 값이 1 미만 (ValidPage 검증 실패)"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "유저를 찾을 수 없음"
            )
    })
    @GetMapping("/me")
    public ApiResponse<ReviewResDTO.ReviewPreViewListDTO> getMyReviews(
            @RequestParam Long userId,
            @RequestParam(name = "page") @ValidPage Integer page
    ) {
        return ApiResponse.onSuccess(
                ReviewSuccessCode.FOUND,
                reviewService.getMyReviews(userId, page)
        );
    }
}
