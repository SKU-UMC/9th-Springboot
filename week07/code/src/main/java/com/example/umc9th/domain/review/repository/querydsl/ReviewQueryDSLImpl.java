package com.example.umc9th.domain.review.repository.querydsl;

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

        //join on -> fetchJoin 변경
        return queryFactory
                .selectFrom(review)
                .join(review.store, store).fetchJoin()
                .join(store.location, location).fetchJoin()
                .where(predicate)
                .fetch();
    }

    @Override
    public List<Review> memberReviews(Predicate predicate){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QReview review = QReview.review;
        QStore store = QStore.store;

        //join on -> fetchJoin 변경
        return queryFactory
                .selectFrom(review)
                .join(review.store, store).fetchJoin()
                .where(predicate)
                .fetch();

    }

}
