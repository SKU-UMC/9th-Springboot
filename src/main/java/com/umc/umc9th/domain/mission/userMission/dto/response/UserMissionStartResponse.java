package com.umc.umc9th.domain.mission.userMission.dto.response;

import com.umc.umc9th.domain.mission.entity.MissionStatus;
import com.umc.umc9th.domain.mission.userMission.entity.UserMission;

public record UserMissionStartResponse(
        Long userMissionId,
        Long missionId,
        Long storeId,
        Long userId,
        MissionStatus status
) {

    public static UserMissionStartResponse from(UserMission userMission) {
        return new UserMissionStartResponse(
                userMission.getId(),
                userMission.getMission().getId(),
                userMission.getMission().getStore().getId(),
                userMission.getUser().getId(),
                userMission.getMissionStatus()
        );
    }
}
