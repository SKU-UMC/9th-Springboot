package com.example.umc_9th_springboot.domain.review.dto.res;

import lombok.Builder;

import java.time.LocalDateTime;

import java.time.LocalDate;

import java.util.List;

public class ReviewResDTO {
    //리뷰 생성
    @Builder
    public record CreateDTO(
            Long reviewId,
            Long userId,
            Long shopId,
            Integer rating,
            String content,
            LocalDateTime createdAt
    ) {}

    //내가 작성한 리뷰 목록 응답 DTO
    @Builder
    public record ReviewPreViewListDTO(
            List<ReviewPreViewDTO> reviewList,
            Integer listSize,
            Integer totalPage,
            Long totalElements,
            Boolean isFirst,
            Boolean isLast
    ) {}

     //단일 리뷰 요약 DTO

    @Builder
    public record ReviewPreViewDTO(
            Long reviewId,
            String ownerNickname,
            double rating,
            String content,
            LocalDate createdAt
    ) {}
}
