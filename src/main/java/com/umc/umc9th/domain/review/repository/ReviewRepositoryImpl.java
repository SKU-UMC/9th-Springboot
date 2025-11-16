package com.umc.umc9th.domain.review.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc.umc9th.domain.review.entity.QReview;
import com.umc.umc9th.domain.review.entity.Review;
import com.umc.umc9th.domain.store.entity.QStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewQueryDsl {

    private final JPAQueryFactory query;

    private static final QReview r = QReview.review;
    private static final QStore s = QStore.store;

    @Override
    public List<Review> searchPagingReviews(Predicate predicate, Pageable pageable) {
        return query
                .selectFrom(r)
                .join(r.store, s).fetchJoin()
                .where(predicate)
                .distinct()
                .orderBy(r.createdAt.desc(), r.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }


}