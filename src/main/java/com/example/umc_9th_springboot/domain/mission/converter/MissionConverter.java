package com.example.umc_9th_springboot.domain.mission.converter;

import com.example.umc_9th_springboot.domain.mission.dto.res.MissionResDTO;
import com.example.umc_9th_springboot.domain.mission.entity.Mission;
import com.example.umc_9th_springboot.domain.mission.entity.UserMission;
import com.example.umc_9th_springboot.domain.mission.enums.MissionStatus;
import com.example.umc_9th_springboot.domain.user.entity.User;

public class MissionConverter {

    // UserMission 엔티티 생성
    public static UserMission toUserMission(User user, Mission mission) {
        return UserMission.builder()
                .mission(mission)
                .user(user)
                .status(MissionStatus.PROGRESS)
                .isReview(false)
                .build();
    }

    // 응답 DTO 변환
    public static MissionResDTO.ChallengeDTO toChallengeDTO(UserMission userMission) {
        return MissionResDTO.ChallengeDTO.builder()
                .userMissionId(userMission.getId())
                .missionId(userMission.getMission().getId())
                .userId(userMission.getUser().getId())
                .status(userMission.getStatus().name())
                .isReview(userMission.isReview())
                .createdAt(userMission.getCreatedAt())
                .build();
    }
}
