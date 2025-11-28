package com.umc.umc9th.domain.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewCreateRequest(

        @NotNull(message = "userId는 필수 값입니다.")
        Long userId,

        @NotNull(message = "storeId는 필수 값입니다.")
        Long storeId,

        @NotBlank(message = "리뷰 내용은 비워둘 수 없습니다.")
        String reviewText,

        @NotNull(message = "리뷰 평점은 필수 값입니다.")
        Double reviewRating

) {}
