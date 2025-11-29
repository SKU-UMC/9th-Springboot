package com.example.umc_9th_springboot.domain.review.dto.res;

import lombok.Builder;

import java.time.LocalDateTime;

public class ReviewResDTO {

    @Builder
    public record CreateDTO(
            Long reviewId,
            Long userId,
            Long shopId,
            Integer rating,
            String content,
            LocalDateTime createdAt
    ) {}
}
