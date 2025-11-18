package com.example.umc9th1.domain.review.service;

import com.example.umc9th1.domain.review.dto.ReviewResponseDto;
import com.example.umc9th1.domain.review.entity.QReview;
import com.example.umc9th1.domain.review.entity.Review;
import com.example.umc9th1.domain.review.exception.ReviewException;
import com.example.umc9th1.domain.review.exception.code.ReviewErrorCode;
import com.example.umc9th1.domain.review.repository.ReviewQueryDsl;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReviewQueryService {

    private final ReviewQueryDsl reviewQueryDsl;

    @Autowired
    public ReviewQueryService(ReviewQueryDsl reviewQueryDsl) {
        this.reviewQueryDsl = reviewQueryDsl;
    }
    public List<ReviewResponseDto> searchReview(Long userId , String storeName , BigDecimal star) {
            //Q 클래스 생성
            QReview review = QReview.review;
        // 불리언 빌더 생성
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(review.user.id.eq(userId));
        //유저 ID NULL 인경우 예외처리
        if (userId == null) {
            throw new ReviewException(ReviewErrorCode.INVALID_PARAMETER);
        }

        if (storeName != null && !storeName.isBlank()) {
            builder.and(review.store.name.contains(storeName));
        }

        if (star != null) {
            if (star.doubleValue() < 0 || star.doubleValue() > 5) {
                throw new ReviewException(ReviewErrorCode.INVALID_STAR_RANGE);
            }
            BigDecimal starFloor = new BigDecimal(star.intValue());
            BigDecimal starCeil = starFloor.add(BigDecimal.ONE);
            builder.and(review.star.goe(starFloor).and(review.star.lt(starCeil)));
        }


        List<Review> reviews = reviewQueryDsl.searchMyReviews(builder);

        if (reviews.isEmpty()) {
            throw new ReviewException(ReviewErrorCode.NO_REVIEWS_FOUND);
        }
        return reviews.stream()
                .map(r -> new ReviewResponseDto(
                        r.getId(),
                        r.getMission().getId(),
                        r.getStore().getId(),
                        r.getStar(),
                        r.getContent()
                ))
                .toList();
    }

}
