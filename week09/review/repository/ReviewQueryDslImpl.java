package com.example.umc9th1.domain.review.repository;

import com.example.umc9th1.domain.review.entity.QReview;
import com.example.umc9th1.domain.review.entity.Review;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import static com.example.umc9th1.domain.review.entity.QReview.review;


@Repository
public class ReviewQueryDslImpl implements ReviewQueryDsl {

    private final EntityManager em;

    @Autowired
    public ReviewQueryDslImpl(EntityManager em){
        this.em = em;
    }

//    @Override
//    public List<Review> searchMyReviews(Predicate predicate) {
//        //JPA 세팅
//        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
//        //Q class 선언
//        QReview review = QReview.review;
//        return queryFactory
//                .select(review)
//                .from(review)
//                .leftJoin(review.store).fetchJoin()
//                .leftJoin(review.mission).fetchJoin()
//                .leftJoin(review.user).fetchJoin()
//                .where(predicate)
//                .distinct()
//                .fetch();
//    }

    @Override
    public Page<Review> search(Long userId, String storeName, BigDecimal star, Pageable pageable) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<Review> content = queryFactory
                .selectFrom(review)
                .where(
                        review.user.id.eq(userId),
                        storeName != null ? review.store.name.contains(storeName) : null,
                        star != null ? review.star.eq(star) : null
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = queryFactory
                .select(review.count())
                .from(review)
                .where(
                        review.user.id.eq(userId),
                        storeName != null ? review.store.name.contains(storeName) : null,
                        star != null ? review.star.eq(star) : null
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, count);
    }

}
