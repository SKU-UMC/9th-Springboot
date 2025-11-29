package com.example.umc_9th_springboot.domain.mission.service;

import com.example.umc_9th_springboot.domain.mission.entity.UserMission;
import com.example.umc_9th_springboot.domain.mission.enums.MissionStatus;
import com.example.umc_9th_springboot.domain.mission.repository.UserMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMissionService {
    private final UserMissionRepository UserMissionRepository;

    //유저 미션 목록 조회
    //page: 0
    //size: 한 페이지당 미션 개수
    public Page<UserMission> getUserMissions(Long userId, MissionStatus status, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return UserMissionRepository.findUserMissionsByStatus(userId, status, pageable);
    }
}
