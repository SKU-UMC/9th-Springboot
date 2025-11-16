package com.umc.umc9th.domain.mission.userMission.dto.response;

public record UserMissionInProgressResponse(
        Long userMissionId, com.umc.umc9th.domain.mission.entity.MissionStatus missionStatus,
        java.time.LocalDateTime createdAt,
        Long missionId, Long missionPoint, Long missionCost,
        Long storeId, String storeName,
        Integer leftDays
) {}