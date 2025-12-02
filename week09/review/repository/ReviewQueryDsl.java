package com.example.umc9th1.domain.review.repository;

import com.example.umc9th1.domain.review.entity.Review;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ReviewQueryDsl {

    // List<Review> searchMyReviews(Predicate predicate);
    Page<Review> search(Long userId, String storeName, BigDecimal star, Pageable pageable);
}
