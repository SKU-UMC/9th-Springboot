package com.umc.springboot.domain.mission.repository;

import com.umc.springboot.domain.mission.entity.UserMission;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

  boolean existsByUserIdAndMissionId(Long userId, Long missionId);

  // 내가 진행중인 미션 목록 (완료되지 않은 것만)
  List<UserMission> findByUserIdAndIsCompletedFalse(Long userId);

  // 특정 유저의 특정 UserMission 조회 (본인 것만 완료할 수 있게)
  Optional<UserMission> findByIdAndUserId(Long id, Long userId);
}
