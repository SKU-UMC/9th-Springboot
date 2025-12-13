package io.api.week09.service;

import io.api.week09.domain.Review;
import io.api.week09.dto.ReviewResponse;
import io.api.week09.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public Page<ReviewResponse> getMyReviews(Long userId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByUserId(userId, pageable);
        return reviews.map(ReviewResponse::from);
    }
    
    // Helper to add reviews for testing
    @Transactional
    public void writeReview(Long userId, String content) {
        reviewRepository.save(Review.builder()
                .userId(userId)
                .content(content)
                .build());
    }
}
