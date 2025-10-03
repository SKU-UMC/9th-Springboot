package com.example.umc9th.domain.mission.service;

import com.example.umc9th.domain.mission.dto.MissionCompleteDTO;
import com.example.umc9th.domain.mission.dto.MissionDTO;
import com.example.umc9th.domain.mission.repository.UserMissionRepository;
import com.example.umc9th.domain.user.entity.UserMission;
import com.example.umc9th.domain.user.service.UserService;
import com.example.umc9th.global.entity.Users;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MissionService {
    private final UserMissionRepository userMissionRepository;
    private final UserService userService;
    @Autowired
    public MissionService(UserMissionRepository userMissionRepository , UserService userService) {
        this.userMissionRepository = userMissionRepository;
        this.userService = userService;
    }
    public List<MissionDTO> getOngoingMissions(Long userId, LocalDateTime lastCreateAt, Long lastMissionId , int limit) {
        List<MissionDTO> missions =  userMissionRepository.findOngoingMissions(userId, lastCreateAt, lastMissionId);
        // limit 적용
        if (missions.size() > limit) {
            return missions.subList(0, limit);
        }
        return missions;
    }
    public List<MissionDTO> getCompleteMissions(Long userId, LocalDateTime lastCreateAt, Long lastMissionId, int limit) {
        List<MissionDTO> missions =  userMissionRepository.findCompletedMissions(userId, lastCreateAt, lastMissionId);
        // limit 적용
        if (missions.size() > limit) {
            return missions.subList(0, limit);
        }
        return missions;
    }

    // 미션 성공 처리
    @Transactional
    public MissionCompleteDTO completeMission(Long userId, Long missionId) {
        Optional<UserMission> userMissionOpt = userMissionRepository.findByUserIdAndMissionIdAndSuccessFalse(userId, missionId);

        if (userMissionOpt.isPresent()) {
            UserMission userMission = userMissionOpt.get();

            //성공처리
            userMission.setSuccess(true);
            userMission.setSuccessAt(LocalDateTime.now());
            userMissionRepository.save(userMission);

            // 미션 포인트 지급
            int missionPoint = userMission.getMission().getPoint();
            Users user = userMission.getUser();
            user.setPoint(user.getPoint() + missionPoint);
            userService.saveUser(user);

            // DTO 반환
            return new MissionCompleteDTO(
                userId , missionId , user.getPoint() , "미션 성공 처리 완료"
            );

        } else {
            throw new IllegalArgumentException("이미 완료되었거나 존재하지 않는 미션입니다.");
        }
    }


}
