package com.example.umc_9th_springboot.domain.review.service;

import com.example.umc_9th_springboot.domain.review.dto.ReviewResponse;
import com.example.umc_9th_springboot.domain.review.enums.StarScore;
import com.example.umc_9th_springboot.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 가게명 + 별점대 API
    public Page<ReviewResponse> getReviews(String shopName, Integer score, Pageable pageable) {
        return reviewRepository.search(shopName, StarScore.from(score), pageable);

    }
}
