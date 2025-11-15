package com.example.umc_9th_springboot.domain.review.repository;

import com.example.umc_9th_springboot.domain.review.dto.ReviewResponse;
import com.example.umc_9th_springboot.domain.review.entity.QReview;
import com.example.umc_9th_springboot.domain.shop.entity.QShop;
import com.example.umc_9th_springboot.domain.review.enums.StarScore;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/** 리뷰 조회 커스텀 구현체 */
@Repository
@RequiredArgsConstructor
    public class ReviewQueryRepositoryImpl implements ReviewQueryRepository {

    private final JPAQueryFactory query; //QueryDSL 쿼리 생성 위한 팩토리
    private final QReview review = QReview.review;
    private final QShop shop = QShop.shop;


    /** 가게명 필터
    - shopName이 null이면 return null
     -값 있으면 가게명 조회
     * */
    private BooleanExpression shopEquals(String shopName) {
        if (shopName == null || shopName.isBlank()) return null;
        return shop.name.eq(shopName);
    }

    /** 별점대 필터
     -StarScore의 min,max값으로 범위 조건
     -점수대 별로 조회 (5점일 경우에만 5점만 조회)
     -StarScore 의 enums에서 min,max 값을 정의해둠
     * */
    private BooleanExpression starInScore(StarScore score) {
        if (score == null) return null;

        double min = score.min();
        double max = score.max();

        if (score == StarScore.FIVE) {
            return review.rating.doubleValue().eq(5.0);
        }

        return review.rating.doubleValue().goe(min)
                .and(review.rating.doubleValue().lt(max));
    }



    /** 가게명 + 별점대 동시/개별 필터 + 페이징
     - shopName과 score가 모두 존재 -> AND 조건 검색
     - 하나만 있으면 -> 단독 조건
     - 둘 다 없으면 -> 전체 리뷰 조회
     * */
    @Override
    public Page<ReviewResponse> search(String shopName, StarScore score, Pageable pageable) {
        //동적 where 절 생성 (조건이 NULL이면 무시)
        BooleanBuilder where = new BooleanBuilder()
                .and(shopEquals(shopName)) //가게명
                .and(starInScore(score)); //별점

        // 본쿼리 : 리뷰 목록 조회
        JPAQuery<ReviewResponse> base = query
                .select(Projections.constructor(
                        ReviewResponse.class, //DTO 생성자 매핑
                        review.id,
                        review.content,
                        review.rating,
                        Expressions.asString(""),
                        shop.name
                ))
                .from(review)
                .leftJoin(review.shop, shop)
                .where(where) //동적 where절 (shopName,rating)
                .orderBy(review.id.desc());

        //페이징 처리
        List<ReviewResponse> content = base
                .offset(pageable.getOffset()) //시작 인덱스
                .limit(pageable.getPageSize()) //페이징 크기
                .fetch(); //결과

        //전체 개수 카운트 쿼리
        //페이징을 하려면 전체 개수가 필요!!
        Long total = query
                .select(review.count()) //count를 한 번 더 해줌
                .from(review)
                .leftJoin(review.shop, shop)
                .where(where)
                .fetchOne();

        //page 객체로 변환
        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }
}
