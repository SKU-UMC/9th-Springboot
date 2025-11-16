package com.umc.umc9th.domain.review.repository;

import com.querydsl.core.types.Predicate;
import com.umc.umc9th.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewQueryDsl {
    List<Review> searchPagingReviews(Predicate predicate, Pageable pageable);
}
