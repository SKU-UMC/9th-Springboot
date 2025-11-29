package com.example.umc_9th_springboot.domain.mission.service;

import com.example.umc_9th_springboot.domain.mission.converter.MissionConverter;
import com.example.umc_9th_springboot.domain.mission.dto.res.MissionResDTO;
import com.example.umc_9th_springboot.domain.mission.entity.Mission;
import com.example.umc_9th_springboot.domain.mission.exception.MissionException;
import com.example.umc_9th_springboot.domain.mission.exception.code.MissionErrorCode;
import com.example.umc_9th_springboot.domain.mission.repository.MissionRepository;
import com.example.umc_9th_springboot.domain.user.entity.User;
import com.example.umc_9th_springboot.domain.user.repository.UserRepository;
import com.example.umc_9th_springboot.domain.mission.entity.UserMission;
import com.example.umc_9th_springboot.domain.mission.repository.UserMissionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;
    private final UserRepository userRepository;

    // 완료한 미션 카운트 - 홈
    public Long getCompletedMissionCount(Long userId) {
        return userMissionRepository.countCompletedMissions(userId);
    }

    // 도전 가능한 미션 목록 조회 - 홈
    public Page<Mission> getAvailableMissions(Long regionId, Long userId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return missionRepository.findAvailableMissionsByRegion(regionId, userId, pageable);
    }

    // 미션 도전하기
    @Transactional
    public MissionResDTO.ChallengeDTO challengeMission(Long missionId) {

        Long tempUserId = 1L;

        // 유저 조회
        User user = userRepository.findById(tempUserId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // 미션 조회
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.NOT_FOUND));

        // UserMission 엔티티 생성
        UserMission userMission = MissionConverter.toUserMission(user, mission);

        // 저장
        UserMission savedUserMission = userMissionRepository.save(userMission);

        // DTO 변환 후 반환
        return MissionConverter.toChallengeDTO(savedUserMission);
    }
}
