package com.example.umc9th.domain.review.repository.querydsl;

import com.example.umc9th.domain.review.entity.Review;
import com.querydsl.core.types.Predicate;

import java.util.List;


public interface ReviewQueryDSL {
    List<Review> searchReview(Predicate predicate);
    List<Review> memberReviews(Predicate predicate);
}
