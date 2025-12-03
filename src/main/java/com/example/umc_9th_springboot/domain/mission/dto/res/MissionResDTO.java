package com.example.umc_9th_springboot.domain.mission.dto.res;

import lombok.Builder;

import java.time.LocalDateTime;

import java.util.List;

import com.example.umc_9th_springboot.domain.mission.enums.MissionStatus;


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

    // 특정 가게 미션 목록 응답 DTO
    @Builder
    public record MissionPreviewListDTO(
            List<MissionPreviewDTO> missionList,
            Integer listSize,
            Integer totalPage,
            Long totalElements,
            Boolean isFirst,
            Boolean isLast
    ) {}


   //하나의 미션 요약 DTO
    @Builder
    public record MissionPreviewDTO(
            Long missionId,
            String title,
            String description
    ) {}

    // 진행 중인 미션 목록 DTO
    @Builder
    public record ProgressMissionListDTO(
            List<ProgressMissionDTO> missionList,
            Integer listSize,
            Integer totalPage,
            Long totalElements,
            Boolean isFirst,
            Boolean isLast
    ) {}


    //진행중 미션 단일 DTO
    @Builder
    public record ProgressMissionDTO(
            Long missionId,
            String title,
            String description,
            MissionStatus status
    ) {}
}
