package com.example.umc_9th_springboot.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 리뷰 조회 응답 DTO */
@Getter
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private String content;
    private Double star;
    private String reply;     // 사장님 답글
    private String shopName;  // 가게명 표시용

}
