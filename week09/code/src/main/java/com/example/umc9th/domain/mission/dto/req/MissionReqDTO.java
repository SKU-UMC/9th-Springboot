package com.example.umc9th.domain.mission.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class MissionReqDTO {
    public record AddDTO(
            @NotNull
            LocalDate deadline,
            @NotBlank
            String missionCondition,
            @NotNull
            Integer point,
            @NotNull
            Long storeId
    ){}

    public record ChallengeDTO(
            @NotNull
            Long memberId,
            @NotNull
            Long missionId
    ){}
}
