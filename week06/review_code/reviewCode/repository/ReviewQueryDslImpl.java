package com.example.umc9th1.domain.review.repository;

import com.example.umc9th1.domain.review.entity.QReview;
import com.example.umc9th1.domain.review.entity.Review;
import com.example.umc9th1.domain.store.entity.QStore;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class ReviewQueryDslImpl implements ReviewQueryDsl {

    private final EntityManager em;

    @Autowired
    public ReviewQueryDslImpl(EntityManager em){
        this.em = em;
    }

    @Override
    public List<Review> searchMyReviews(Predicate predicate) {
        //JPA 세팅
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        //Q class 선언
        QReview review = QReview.review;

        return queryFactory
                .select(review)
                .from(review)
                .leftJoin(review.store).fetchJoin()
                .leftJoin(review.mission).fetchJoin()
                .leftJoin(review.user).fetchJoin()
                .where(predicate)
                .distinct()
                .fetch();
    }
}
