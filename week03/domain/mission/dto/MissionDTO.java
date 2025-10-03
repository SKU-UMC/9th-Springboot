package com.example.umc9th.domain.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MissionDTO {

    private Long missionId;       // 미션 ID
    private String storeName;     // 가게 이름
    private Integer point;        // 미션 포인트
    private String missionCondition; // 미션 조건
    private LocalDateTime startAt;   // 미션 시작 시간
}