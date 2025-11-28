package com.umc.umc9th.domain.review.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.umc.umc9th.domain.review.dto.request.ReviewCreateRequest;
import com.umc.umc9th.domain.review.dto.response.MyReviewsSearchResponse;
import com.umc.umc9th.domain.review.dto.response.ReviewCreateResponse;
import com.umc.umc9th.domain.review.entity.QReview;
import com.umc.umc9th.domain.review.entity.Review;
import com.umc.umc9th.domain.review.repository.ReviewRepository;
import com.umc.umc9th.domain.store.entity.Store;
import com.umc.umc9th.domain.store.repository.StoreRepository;
import com.umc.umc9th.domain.user.entity.User;
import com.umc.umc9th.domain.user.repository.UserRepository;
import com.umc.umc9th.global.error.code.ErrorCode;
import com.umc.umc9th.global.error.code.GlobalErrorCode;
import com.umc.umc9th.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;


    /**
     * ratingFilter: 0(전체) | 1~5(해당 점수대)
     * storeId: null이면 모든 가게
     */
    @Transactional(readOnly = true)
    public Page<MyReviewsSearchResponse> getMyReviews(Long userId, Long storeId, Integer ratingFilter, Pageable pageable) {
        QReview r = QReview.review;

        Predicate predicate = Expressions.allOf(
                r.user.id.eq(userId),
                storeId == null ? null : r.store.id.eq(storeId),
                (ratingFilter == null || ratingFilter == 0)
                        ? null : r.reviewRating.goe(ratingFilter.doubleValue())
                        .and(r.reviewRating.lt(ratingFilter + 1.0))
        );

        long total = reviewRepository.count(predicate);
        List<Review> reviews = reviewRepository.searchPagingReviews(predicate, pageable);
        if (reviews.isEmpty()) return Page.empty();

        List<MyReviewsSearchResponse> dtoList = reviews.stream()
                .sorted(Comparator.comparing(Review::getCreatedAt).reversed()
                        .thenComparing(Review::getId).reversed())
                .map(v -> new MyReviewsSearchResponse(
                        v.getId(),
                        v.getStore().getId(),
                        v.getStore().getStoreName(),
                        v.getReviewRating(),
                        v.getReviewText(),
                        v.getCreatedAt(),
                        null,
                        null
                ))
                .toList();

        return new PageImpl<>(dtoList, pageable, total);
    }

    /**
     * 리뷰 작성
     */
    @Transactional
    public ReviewCreateResponse createReview(Long storeId,
                                             Long userId,
                                             ReviewCreateRequest request) {

        // 스토어 조회
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));

        // 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));

        // 리뷰 생성
        Review review = Review.builder()
                .reviewText(request.reviewText())
                .reviewRating(request.reviewRating())
                .user(user)
                .store(store)
                .build();


        // 저장
        Review saved = reviewRepository.save(review);

        // 팩토리 패턴 - 저장마친 리뷰엔티티를 리턴dto화
        return ReviewCreateResponse.from(saved);
    }
}