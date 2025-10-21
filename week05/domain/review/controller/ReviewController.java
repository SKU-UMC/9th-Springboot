package com.example.umc9th1.domain.review.controller;

import com.example.umc9th1.domain.review.dto.ReviewRequestDto;
import com.example.umc9th1.domain.review.dto.ReviewResponseDto;
import com.example.umc9th1.domain.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users/{userId}/missions")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{missionId}/review")
    public ResponseEntity<ReviewResponseDto> createReview(
            @PathVariable Long userId, //유저 아이디
            @RequestBody ReviewRequestDto dto, //리뷰 dto
            @PathVariable Long missionId ){ //미션 아이디

        ReviewResponseDto response=reviewService.createReview(userId,missionId , dto); //서비스 호출

        return ResponseEntity.status(HttpStatus.CREATED).body(response); //201 create
    }



}
