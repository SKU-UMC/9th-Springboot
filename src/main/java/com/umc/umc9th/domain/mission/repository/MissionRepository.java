package com.umc.umc9th.domain.mission.repository;

import com.umc.umc9th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    Optional<Mission> findByIdAndStoreId(Long missionId, Long storeId);

    // 해당 가게의 미션 목록을 페이징으로 조회
    Page<Mission> findByStoreId(Long storeId, Pageable pageable);

    // 유저가 진행중인 미션 목록을 페이징으로 조회


}
