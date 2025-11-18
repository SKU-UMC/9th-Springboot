package com.example.umc9th1.domain.review.controller;

import com.example.umc9th1.domain.review.dto.ReviewRequestDto;
import com.example.umc9th1.domain.review.dto.ReviewResponseDto;
import com.example.umc9th1.domain.review.service.ReviewQueryService;
import com.example.umc9th1.domain.review.service.ReviewService;
import com.example.umc9th1.global.apipayload.ApiResponse;
import com.example.umc9th1.global.apipayload.code.GeneralSuccessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/users/{userId}")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewQueryService reviewQueryService;

    @Autowired
    public ReviewController(ReviewService reviewService, ReviewQueryService reviewQueryService) {
        this.reviewService = reviewService;
        this.reviewQueryService = reviewQueryService;
    }
  
    @PostMapping("/missions/{missionId}/reviews")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> createReview(
            @PathVariable Long userId, //유저 아이디
            @RequestBody ReviewRequestDto dto, //리뷰 dto
            @PathVariable Long missionId ){ //미션 아이디

        ReviewResponseDto response=reviewService.createReview(userId,missionId , dto); //서비스 호출
        GeneralSuccessCode code  = GeneralSuccessCode.REVIEW_CREATED;
        return ResponseEntity.status(code.getStatus()).body(ApiResponse.onSuccess(code , response)); //201 create
    }

    @GetMapping("/reviews")
    public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> searchMyReviews( @PathVariable Long userId,
                                         @RequestParam(required = false) String storeName,
                                         @RequestParam(required = false) BigDecimal star  ){

        List<ReviewResponseDto> result = reviewQueryService.searchReview(userId,storeName,star);
        GeneralSuccessCode code  = GeneralSuccessCode.SUCCESS;
        return  ResponseEntity.status(code.getStatus()).body(ApiResponse.onSuccess(code,result));
    }



}
