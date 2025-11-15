package com.example.umc_9th_springboot.domain.review.repository;

import com.example.umc_9th_springboot.domain.review.dto.ReviewResponse;
import com.example.umc_9th_springboot.domain.review.enums.StarScore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** 리뷰 조회 커스텀 쿼리 인터페이스 */
public interface ReviewQueryRepository {
    /** 가게명 + 별점대 필터 API */
    Page<ReviewResponse> search(String shopName, StarScore score, Pageable pageable);

}