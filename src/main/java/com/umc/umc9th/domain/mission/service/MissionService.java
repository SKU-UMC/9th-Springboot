package com.umc.umc9th.domain.mission.service;

import com.umc.umc9th.domain.mission.entity.MissionStatus;
import com.umc.umc9th.domain.mission.userMission.dto.response.UserMissionStartResponse;
import com.umc.umc9th.domain.mission.entity.Mission;
import com.umc.umc9th.domain.mission.repository.MissionRepository;
import com.umc.umc9th.domain.mission.userMission.entity.UserMission;
import com.umc.umc9th.domain.mission.userMission.repository.UserMissionRepository;
import com.umc.umc9th.domain.store.entity.Store;
import com.umc.umc9th.domain.store.repository.StoreRepository;
import com.umc.umc9th.domain.user.entity.User;
import com.umc.umc9th.domain.user.repository.UserRepository;
import com.umc.umc9th.global.error.code.GlobalErrorCode;
import com.umc.umc9th.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final StoreRepository storeRepository;
    private final MissionRepository missionRepository;
    private final UserRepository userRepository;
    private final UserMissionRepository userMissionRepository;

    @Transactional
    public UserMissionStartResponse startMission(Long storeId,
                                                     Long missionId,
                                                     Long userId) {

        // 가게 조회
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));

        // 해당 가게의 미션인지 검증 미션 조회
        Mission mission = missionRepository.findByIdAndStoreId(missionId, store.getId())
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));


        // 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));

        // 이미 도전 중인지 여부 확인 (중복 방지)
        if (userMissionRepository.existsByUserAndMission(user, mission)) {
            throw new CustomException(GlobalErrorCode.BAD_REQUEST); // 필요하면 USER_MISSION_ALREADY_EXISTS 같은 코드 따로 파도 됨
        }

        // UserMission 생성
        UserMission userMission = UserMission.inProgressing(mission, user, MissionStatus.IN_PROGRESS);

        // 저장
        UserMission saved = userMissionRepository.save(userMission);

        // 정적 팩토리 패턴으로 dto 리턴
        return UserMissionStartResponse.from(saved);
    }
}
