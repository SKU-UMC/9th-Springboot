package com.umc.springboot.domain.mission.repository;

import com.umc.springboot.domain.mission.entity.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

  boolean existsByUserIdAndMissionId(Long userId, Long missionId);
}
