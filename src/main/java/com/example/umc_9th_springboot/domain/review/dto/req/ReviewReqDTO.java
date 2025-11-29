package com.example.umc_9th_springboot.domain.review.dto.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewReqDTO {

    public record CreateDTO(
            @NotNull(message = "별점은 필수 값입니다.")
            @Min(value = 1, message = "별점은 최소 1점이어야 합니다.")
            @Max(value = 5, message = "별점은 최대 5점이어야 합니다.")
            Integer rating,

            @NotBlank(message = "리뷰 내용은 비어 있을 수 없습니다.")
            String content,

            @NotNull(message = "가게 ID는 필수 값입니다.")
            Long shopId
    ) {}
}
