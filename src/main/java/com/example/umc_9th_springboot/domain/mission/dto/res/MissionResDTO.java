package com.example.umc_9th_springboot.domain.mission.dto.res;

import lombok.Builder;

import java.time.LocalDateTime;

public class MissionResDTO {

    @Builder
    public record ChallengeDTO(
            Long userMissionId,
            Long missionId,
            Long userId,
            String status,
            Boolean isReview,
            LocalDateTime createdAt
    ){}
}
