package com.umc.umc9th.domain.mission.service;

import com.umc.umc9th.domain.mission.dto.response.StoreMissionsSearchResponse;
import com.umc.umc9th.domain.mission.entity.MissionStatus;
import com.umc.umc9th.domain.mission.userMission.dto.response.UserMissionInProgressResponse;
import com.umc.umc9th.domain.mission.userMission.dto.response.UserMissionStartResponse;
import com.umc.umc9th.domain.mission.entity.Mission;
import com.umc.umc9th.domain.mission.repository.MissionRepository;
import com.umc.umc9th.domain.mission.userMission.dto.response.UserMissionSuccessResponse;
import com.umc.umc9th.domain.mission.userMission.entity.UserMission;
import com.umc.umc9th.domain.mission.userMission.repository.UserMissionRepository;
import com.umc.umc9th.domain.store.entity.Store;
import com.umc.umc9th.domain.store.repository.StoreRepository;
import com.umc.umc9th.domain.user.entity.User;
import com.umc.umc9th.domain.user.repository.UserRepository;
import com.umc.umc9th.global.error.code.GlobalErrorCode;
import com.umc.umc9th.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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

        // UserMission 생성 - 미션 진행 시작
        UserMission userMission = UserMission.inProgressing(mission, user);

        // 저장
        UserMission saved = userMissionRepository.save(userMission);

        // 정적 팩토리 패턴으로 dto 리턴
        return UserMissionStartResponse.from(saved);
    }

    // 가게의 미션 조회
    @Transactional(readOnly = true)
    public Page<StoreMissionsSearchResponse> getStoreMissions(Long storeId, int page) {

        // page 값 검증
        if (page < 1) {
            throw new CustomException(GlobalErrorCode.INVALID_PARAMETER);
        }

        Pageable pageable = PageRequest.of(
                page - 1,
                10,
                Sort.by(Sort.Direction.DESC, "createdAt")
                        .and(Sort.by(Sort.Direction.DESC, "id"))
        );

        // 해당 가게의 미션 페이징 조회
        Page<Mission> missions = missionRepository.findByStoreId(storeId, pageable);

        // 엔티티 Page -> DTO Page
        return missions.map(StoreMissionsSearchResponse::from);
    }


    @Transactional(readOnly = true)
    public Page<UserMissionInProgressResponse> getInProgressUserMissions(Long userId, int page) {

        // page 값 검증
        if (page < 1) {
            throw new CustomException(GlobalErrorCode.INVALID_PARAMETER);
        }

        Pageable pageable = PageRequest.of(
                page - 1,
                10,
                Sort.by(Sort.Direction.DESC, "createdAt")
                        .and(Sort.by(Sort.Direction.DESC, "id"))
        );

        // 해당 유저의 유저미션 페이징 조회
        Page<UserMission> userMissions =
                userMissionRepository.findUserMissionsByUser_Id(userId, pageable);

        // 엔티티 Page -> DTO Page 변환 + 남은 일수(leftDays) 계산
        return userMissions.map(userMission -> {

            LocalDate createdDate = userMission.getCreatedAt().toLocalDate();
            Integer missionDueDays = userMission.getMission().getMissionDue();  // Mission.missionDue: 일 단위

            LocalDate deadlineDate = createdDate.plusDays(missionDueDays);

            int leftDays = (int) ChronoUnit.DAYS.between(LocalDate.now(), deadlineDate);
            if (leftDays < 0) {
                leftDays = 0;
            }

            return new UserMissionInProgressResponse(
                    userMission.getId(),
                    userMission.getMission().getMissionStatus(),
                    userMission.getCreatedAt(),
                    userMission.getMission().getId(),
                    userMission.getMission().getMissionPoint(),
                    userMission.getMission().getMissionCost(),
                    userMission.getMission().getStore().getId(),
                    userMission.getMission().getStore().getStoreName(),
                    leftDays
            );
        });
    }

    @Transactional
    public UserMissionSuccessResponse completeUserMission(Long userId, Long userMissionId) {

        // 해당 유저의 유저 미션 검증
        UserMission userMission = userMissionRepository.findByIdAndUser_Id(userMissionId, userId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND));

        // IN_PROGRESS 검증
        if (userMission.getMission().getMissionStatus() != MissionStatus.IN_PROGRESS) {
            throw new CustomException(GlobalErrorCode.BAD_REQUEST);
        }

        // 상태 변경 -> SUCCESS
        userMission.updateMissionStatus(MissionStatus.SUCCESS);

        // DTO
        return new UserMissionSuccessResponse(
                userMission.getId(),
                userMission.getMission().getMissionStatus(),
                userMission.getCreatedAt(),                // createdAt 그대로 반환
                userMission.getMission().getId(),
                userMission.getMission().getMissionPoint(),
                userMission.getMission().getMissionCost(),
                userMission.getMission().getStore().getId(),
                userMission.getMission().getStore().getStoreName()
        );
    }
}
