package com.example.umc9th.domain.mission.dto.res;

import com.example.umc9th.domain.mission.entity.Mission;
import lombok.Builder;

import java.time.LocalDate;
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

    @Builder
    public record MissionDTO(
            Long missionId,
            LocalDate deadline,
            String missionCondition,
            Integer point
    ){
        public static MissionDTO of(Mission mission) {
            return MissionDTO.builder()
                    .missionId(mission.getId())
                    .deadline(mission.getDeadline())
                    .missionCondition(mission.getMissionCondition())
                    .point(mission.getPoint())
                    .build();
        }
    }
}
