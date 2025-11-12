package com.umc.springboot.domain.review.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc.springboot.domain.review.dto.request.ReviewRequest;
import com.umc.springboot.domain.review.entity.Review;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.umc.springboot.domain.review.entity.QReview.review;
import static com.umc.springboot.domain.store.entity.QStore.store;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepositoryImpl implements ReviewQueryRepository {

  private final JPAQueryFactory query;

  @Override
  public Page<Review> findMyReviews(Long userId, ReviewRequest filter, Pageable pageable) {
    BooleanBuilder where = buildWhere(userId, filter);

    List<Review> content = query
        .selectFrom(review)
        .leftJoin(review.store, store).fetchJoin() // 단일 연관만 fetchJoin (컬렉션 X)
        .where(where)
        .orderBy(review.createdAt.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = query
        .select(review.count())
        .from(review)
        .where(where)
        .fetchOne();

    return new PageImpl<>(content, pageable, total == null ? 0 : total);
  }

  private BooleanBuilder buildWhere(Long userId, ReviewRequest filter) {
    BooleanBuilder where = new BooleanBuilder();
    where.and(review.user.id.eq(userId));

    if (filter == null) {
      return where;
    }

    if (filter.getStoreName() != null && !filter.getStoreName().isBlank()) {
      where.and(review.store.name.eq(filter.getStoreName()));
    }

    if (filter.getRatingBand() != null) {
      int band = filter.getRatingBand();
      BigDecimal lower = BigDecimal.valueOf(band).setScale(1);

      if (band == 5) {
        where.and(review.rating.eq(BigDecimal.valueOf(5.0).setScale(1)));
      } else {
        BigDecimal upper = BigDecimal.valueOf(band + 1).setScale(1);
        where.and(review.rating.goe(lower).and(review.rating.lt(upper)));
      }
    }

    return where;
  }
}
