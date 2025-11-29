package com.example.umc_9th_springboot.domain.mission.repository;

import com.example.umc_9th_springboot.domain.mission.entity.Mission;
import com.example.umc_9th_springboot.domain.mission.entity.UserMission;
import com.example.umc_9th_springboot.domain.mission.enums.MissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MissionRepository extends JpaRepository<Mission, Long> {
    // 해당 지역에서 사용자가 아직 완료하지 않은 미션 조회(페이징)
    @Query("select m from Mission m " +
            "join fetch m.shop s " +
            "join fetch s.region r " +
            "left join UserMission um on um.mission = m and um.user.id = :userId " +
            "where r.id = :regionId " +
            "and (um.status is null or um.status <> 'COMPLETED')")
    Page<Mission> findAvailableMissionsByRegion(
            @Param("regionId") Long regionId,
            @Param("userId") Long userId,
            Pageable pageable
    );
}
