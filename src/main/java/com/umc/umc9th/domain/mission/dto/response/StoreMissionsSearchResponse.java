package com.umc.umc9th.domain.mission.dto.response;

import com.umc.umc9th.domain.mission.entity.Mission;
import com.umc.umc9th.domain.mission.entity.MissionStatus;

public record StoreMissionsSearchResponse(
        Long missionId,
        Long storeId,
        String storeName,
        Long missionPoint,
        String missionCode,
        Long missionCost,
        Integer missionDue,
        MissionStatus missionStatus
) {
    // 엔티티 -> DTO 정적 팩토리 메서드
    public static StoreMissionsSearchResponse from(Mission mission) {
        return new StoreMissionsSearchResponse(
                mission.getId(),
                mission.getStore().getId(),
                mission.getStore().getStoreName(),
                mission.getMissionPoint(),
                mission.getMissionCode(),
                mission.getMissionCost(),
                mission.getMissionDue(),
                mission.getMissionStatus()
        );
    }
}