package com.example.umc9th.domain.Review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//리뷰 작성페이지화면 get하기위한 DTO
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewWriteDTO {

    private Long missionId;   //미션 아이디
    private Long storeId;     // 가게 아이디
    private String storeName; //가게이름

}
