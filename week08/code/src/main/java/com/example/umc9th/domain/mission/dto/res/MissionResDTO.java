package com.example.umc9th.domain.mission.dto.res;

import lombok.Builder;

import java.time.LocalDateTime;

public class MissionResDTO {
    @Builder
    public record AddDTO(
       Long missionId,
       LocalDateTime createAt
    ){}

    @Builder
    public record ChallengeDTO(
            Long memberMissionId,
            LocalDateTime createAt
    ){}
}
