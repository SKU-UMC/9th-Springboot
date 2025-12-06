package com.umc.springboot.domain.mission.service;

import com.umc.springboot.domain.mission.converter.MissionConverter;
import com.umc.springboot.domain.mission.dto.request.MissionCreateRequest;
import com.umc.springboot.domain.mission.dto.response.MissionResponse;
import com.umc.springboot.domain.mission.dto.response.UserMissionResponse;
import com.umc.springboot.domain.mission.entity.Mission;
import com.umc.springboot.domain.mission.entity.UserMission;
import com.umc.springboot.domain.mission.repository.MissionRepository;
import com.umc.springboot.domain.mission.repository.UserMissionRepository;
import com.umc.springboot.domain.store.entity.Store;
import com.umc.springboot.domain.store.repository.StoreRepository;
import com.umc.springboot.domain.user.entity.User;
import com.umc.springboot.domain.user.repository.UserRepository;
import com.umc.springboot.global.exception.CustomException;
import com.umc.springboot.global.exception.GlobalErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

  private final MissionRepository missionRepository;
  private final UserMissionRepository userMissionRepository;
  private final StoreRepository storeRepository;
  private final UserRepository userRepository;

  /**
   * 가게에 미션 추가
   */
  @Transactional
  public MissionResponse createMissionForStore(Long storeId, MissionCreateRequest req) {

    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));

    Mission mission = MissionConverter.toEntity(req, store);
    missionRepository.save(mission);

    return MissionConverter.toResponse(mission);
  }

  /**
   * 미션 도전하기 (UserMission 생성)
   */
  @Transactional
  public UserMissionResponse challengeMission(Long userId, Long missionId) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));

    Mission mission = missionRepository.findById(missionId)
        .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));

    // 이미 도전 중인지 체크
    if (userMissionRepository.existsByUserIdAndMissionId(userId, missionId)) {
      throw new CustomException(
          GlobalErrorCode.INVALID_INPUT_VALUE); // 필요하면 MissionErrorCode로 분리 가능
    }

    UserMission userMission = UserMission.builder()
        .user(user)
        .mission(mission)
        .isCompleted(false)
        .build();

    userMissionRepository.save(userMission);

    return MissionConverter.toUserMissionResponse(userMission);
  }

  /**
   * 특정 가게의 미션 목록 조회
   */
  @Transactional
  public List<MissionResponse> getMissionsByStore(Long storeId) {
    // 가게 존재 여부 체크 (없으면 404)
    storeRepository.findById(storeId)
        .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));

    List<Mission> missions = missionRepository.findByStoreId(storeId);

    return missions.stream()
        .map(MissionConverter::toResponse)
        .collect(Collectors.toList());
  }

  /**
   * 내가 진행중인 미션 목록 조회 (isCompleted = false)
   */
  @Transactional
  public List<UserMissionResponse> getInProgressMissionsByUser(Long userId) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));

    List<UserMission> userMissions =
        userMissionRepository.findByUserIdAndIsCompletedFalse(user.getId());

    return userMissions.stream()
        .map(MissionConverter::toUserMissionResponse)
        .collect(Collectors.toList());
  }

  /**
   * 진행 중인 미션 완료 처리
   */
  @Transactional
  public UserMissionResponse updateUserMissionStatus(Long userId, Long userMissionId,
      Boolean isCompleted) {

    // 본인 데이터인지 확인
    UserMission userMission = userMissionRepository.findByIdAndUserId(userMissionId, userId)
        .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));

    if (isCompleted == null) {
      throw new CustomException(GlobalErrorCode.INVALID_INPUT_VALUE);
    }

    // 상태 변경
    userMission.updateCompletion(isCompleted);

    return MissionConverter.toUserMissionResponse(userMission);
  }

}
