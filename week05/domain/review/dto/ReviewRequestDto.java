package com.example.umc9th1.domain.review.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDto {
    private double star; //별점
    private String content; //리뷰내용
}
