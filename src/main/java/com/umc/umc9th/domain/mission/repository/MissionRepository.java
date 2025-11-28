package com.umc.umc9th.domain.mission.repository;

import com.umc.umc9th.domain.mission.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    Optional<Mission> findByIdAndStoreId(Long missionId, Long storeId);
}
