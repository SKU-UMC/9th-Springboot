package com.example.umc9th.domain.Review.controller;

import com.example.umc9th.domain.Review.dto.ReviewCreateDTO;
import com.example.umc9th.domain.Review.dto.ReviewResponseDTO;
import com.example.umc9th.domain.Review.dto.ReviewWriteDTO;
import com.example.umc9th.domain.Review.service.ReviewService;
import com.example.umc9th.domain.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/missions")
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthService authService;

    @Autowired
    public ReviewController(ReviewService reviewService, AuthService authService) {
        this.reviewService = reviewService;
        this.authService = authService;
    }

        // GET: 리뷰 작성 페이지
        @GetMapping("/{missionId}/review/write")
        public ReviewWriteDTO getReviewWrite(
                @PathVariable Long missionId,
                @RequestHeader("Authorization") String token
        ) {
            Long userId = authService.getUserIdFromToken(token);
            return reviewService.getReviewWriteData(userId , missionId);
        }

        // POST: 리뷰 등록
        @PostMapping("/{missionId}/review")
        public ReviewResponseDTO createReview(
                @RequestHeader("Authorization") String token,
                @PathVariable Long missionId,
                @RequestBody ReviewCreateDTO dto
        ) {
            Long userId = authService.getUserIdFromToken(token);
            return reviewService.createReview(userId, missionId, dto);
        }
    }
