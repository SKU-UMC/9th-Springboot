package com.example.umc9th.domain.review.controller;

import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.service.ReviewQueryService;
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

    @GetMapping("/reviews/search")
    public List<Review> searchReview(@RequestParam String query, @RequestParam String type) {
        List<Review> result = reviewQueryService.searchReview(query, type);
        return result;
    }

    @GetMapping("/reviews/members/{memberId}")
    public List<Review> memberReviews(@PathVariable String memberId, @RequestParam String query, @RequestParam String type) {
        List<Review> result = reviewQueryService.memberReviews(Long.parseLong(memberId), query, type);
        return result;

    }
}
