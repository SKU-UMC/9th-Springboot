package com.example.umc9th.domain.review.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ReviewResDTO {

    @Getter
    @Builder
    public static class Search{
        private Long reviewId;
        private String content;
        private Float star;
        private Long memberId;
        private Long storeId;
        private Long replyId;
    }

    @Builder
    public record AddDTO(
            Long reviewId,
            LocalDateTime createAt
    ){}


}
