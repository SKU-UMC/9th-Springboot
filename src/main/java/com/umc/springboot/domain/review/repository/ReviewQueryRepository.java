package com.umc.springboot.domain.review.repository;

import com.umc.springboot.domain.review.dto.request.ReviewRequest;
import com.umc.springboot.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewQueryRepository {

  Page<Review> findMyReviews(Long userId, ReviewRequest filter, Pageable pageable);
}