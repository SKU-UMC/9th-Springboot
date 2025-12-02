package com.example.umc9th1.domain.users.dto;

import com.example.umc9th1.domain.store.entity.StoreCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserHomeDto {
    private String address; // 지역
    private long successCount; //유저가 미션 성공한 개수
    private StoreCategory storeCategory; //가게 카테고리
    private Long missionId; //미션 아이디
    private String storeName; //미션제공 가게이름
    private String conditions; //미션 내용
    private int point; //미션이 주는 포인트
    private LocalDate endDate; //미션 마감일
    private LocalDateTime createAt; //미션의 생성일자


}
