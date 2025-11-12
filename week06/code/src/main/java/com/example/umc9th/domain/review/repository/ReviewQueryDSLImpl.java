package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.entity.QReview;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.store.entity.QLocation;
import com.example.umc9th.domain.store.entity.QStore;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
@RequiredArgsConstructor
public class ReviewQueryDSLImpl implements ReviewQueryDSL {

    private final EntityManager em;

    @Override
    public List<Review> searchReview(Predicate predicate){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QReview review = QReview.review;
        QStore store = QStore.store;
        QLocation location = QLocation.location;

        return queryFactory
                .selectFrom(review)
                .join(store).on(store.id.eq(review.store.id))
                .join(location).on(location.id.eq(store.location.id))
                .where(predicate)
                .fetch();
    }

    @Override
    public List<Review> memberReviews(Predicate predicate){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QReview review = QReview.review;
        QStore store = QStore.store;

        return queryFactory
                .selectFrom(review)
                .join(store).on(store.id.eq(review.store.id))
                .where(predicate)
                .fetch();

    }

}
