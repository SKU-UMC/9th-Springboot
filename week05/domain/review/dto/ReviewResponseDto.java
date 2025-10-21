package com.example.umc9th1.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewResponseDto {
    private Long reviewId; //리뷰아이디
    private Long missionId;  //미션아이디
    private Long storeId;    // 가게아이디
    private double star; //별점
    private String content; //내용
}
