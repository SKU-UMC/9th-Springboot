package com.example.umc9th.domain.Review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {
    private Long reviewId;
    private String message;
}
