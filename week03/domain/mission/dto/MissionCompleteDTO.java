package com.example.umc9th.domain.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MissionCompleteDTO {
    private Long userId;      // 요청 시 사용자의 ID
    private Long missionId;   // 요청 시 미션 ID
    private Integer updatedPoint; // 미션 성공 후 업데이트된 포인트
    private String message;       // 성공 메시지
}
