package com.example.umc9th.domain.review.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReviewReqDTO {

    public record AddDTO(
            @NotBlank
            @Size(min = 1, max = 1000)
            String content,
            @NotNull
            @Size(min = 1, max = 5)
            Float star,
            @NotNull
            Long memberId,
            @NotNull
            Long storeId
    ){}
}
