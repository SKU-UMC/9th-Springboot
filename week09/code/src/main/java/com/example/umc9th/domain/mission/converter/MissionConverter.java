package com.example.umc9th.domain.mission.converter;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.entity.mapping.MemberMission;
import com.example.umc9th.domain.mission.dto.req.MissionReqDTO;
import com.example.umc9th.domain.mission.dto.res.MissionResDTO;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.store.entity.Store;

public class MissionConverter {

    public static Mission toAddEntity(MissionReqDTO.AddDTO dto, Store store){
        return Mission.builder()
                .deadline(dto.deadline())
                .missionCondition(dto.missionCondition())
                .point(dto.point())
                .store(store)
                .build();
    }

    public static MissionResDTO.AddDTO toAddDTO(Mission mission){
        return MissionResDTO.AddDTO.builder()
                .missionId(mission.getId())
                .createAt(mission.getCreatedAt())
                .build();
    }

    public static MemberMission toChallengeEntity(Mission mission, Member member){
        return MemberMission.builder()
                .isCompleted(false)
                .mission(mission)
                .member(member)
                .build();
    }

    public static MissionResDTO.ChallengeDTO toChallengeDTO(MemberMission memberMission){
        return MissionResDTO.ChallengeDTO.builder()
                .memberMissionId(memberMission.getId())
                .createAt(memberMission.getCreatedAt())
                .build();
    }
}
