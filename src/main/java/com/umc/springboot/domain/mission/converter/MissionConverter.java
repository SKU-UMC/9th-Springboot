package com.umc.springboot.domain.mission.converter;

import com.umc.springboot.domain.mission.dto.request.MissionCreateRequest;
import com.umc.springboot.domain.mission.dto.response.MissionResponse;
import com.umc.springboot.domain.mission.dto.response.UserMissionResponse;
import com.umc.springboot.domain.mission.entity.Mission;
import com.umc.springboot.domain.mission.entity.UserMission;
import com.umc.springboot.domain.store.entity.Store;

public class MissionConverter {

  public static Mission toEntity(MissionCreateRequest req, Store store) {
    return Mission.builder()
        .store(store)
        .title(req.getTitle())
        .description(req.getDescription())
        .deadline(req.getDeadline())
        .point(req.getPoint())
        .date(req.getDate())
        .build();
  }

  public static MissionResponse toResponse(Mission mission) {
    return MissionResponse.builder()
        .missionId(mission.getId())
        .storeId(mission.getStore() != null ? mission.getStore().getId() : null)
        .title(mission.getTitle())
        .description(mission.getDescription())
        .deadline(mission.getDeadline())
        .point(mission.getPoint())
        .date(mission.getDate())
        .build();
  }

  public static UserMissionResponse toUserMissionResponse(UserMission userMission) {
    Mission mission = userMission.getMission();
    return UserMissionResponse.builder()
        .userMissionId(userMission.getId())
        .missionId(mission.getId())
        .storeId(mission.getStore() != null ? mission.getStore().getId() : null)
        .title(mission.getTitle())
        .isCompleted(userMission.getIsCompleted())
        .build();
  }
}
