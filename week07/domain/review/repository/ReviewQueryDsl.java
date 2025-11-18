package com.example.umc9th1.domain.review.repository;

import com.example.umc9th1.domain.review.entity.Review;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewQueryDsl {

     List<Review> searchMyReviews(Predicate predicate);
}
