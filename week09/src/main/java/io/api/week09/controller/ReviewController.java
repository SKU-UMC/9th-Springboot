package io.api.week09.controller;

import io.api.week09.annotation.ReviewPage;
import io.api.week09.dto.ReviewResponse;
import io.api.week09.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/me")
    public ResponseEntity<Page<ReviewResponse>> getMyReviews(
            @RequestHeader("X-User-Id") Long userId,
            @ReviewPage Pageable pageable
    ) {
        return ResponseEntity.ok(reviewService.getMyReviews(userId, pageable));
    }

    @PostMapping
    public ResponseEntity<Void> writeReview(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody String content
    ) {
        reviewService.writeReview(userId, content);
        return ResponseEntity.ok().build();
    }
}
