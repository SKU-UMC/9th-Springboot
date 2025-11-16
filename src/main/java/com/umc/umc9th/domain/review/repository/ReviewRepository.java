package com.umc.umc9th.domain.review.repository;

import com.umc.umc9th.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewQueryDsl, QuerydslPredicateExecutor<Review> {

    // 리뷰 작성
    @Query("""
    insert into Review (user, store, reviewRating, reviewText)
    select u, s, :rating, :text
    from User u, Store s
    where u.id = :userId and s.id = :storeId
    """) int saveReviewJpql(@Param("userId") Long userId,
                         @Param("storeId") Long storeId,
                         @Param("rating") Double rating,
                         @Param("text") String text
    );

}