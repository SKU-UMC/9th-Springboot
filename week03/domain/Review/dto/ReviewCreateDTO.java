package com.example.umc9th.domain.Review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 리뷰
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateDTO {
    private Long storeId; //가게 아이디
    private Double star; // 별점
    private String content; //내용
}
