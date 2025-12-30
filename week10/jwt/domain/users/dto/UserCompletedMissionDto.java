package com.example.umc9th1.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCompletedMissionDto {
    private Long missionId; //미션아이디
    private String missionCondition; //미션내용
    private Integer missionPoint; //미션 포인트
    private String storeName; //가게 이름
    private LocalDateTime successAt; // 성공일자
}
