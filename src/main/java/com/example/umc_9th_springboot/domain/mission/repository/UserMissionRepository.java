package com.example.umc_9th_springboot.domain.mission.repository;

import com.example.umc_9th_springboot.domain.mission.entity.UserMission;
import com.example.umc_9th_springboot.domain.mission.enums.MissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {
//루트 엔티티가 어디인지에 따라 나눔
//유저는 필터 역할이고 지역에서 미션 목록을 조회한다 => 미션레포

    //상태별 페이지 목록 조회 (페이징)
    @Query("select um from UserMission um " +
            "join fetch um.mission m " +
            "join fetch m.shop s " +
            "where um.user.id = :userId " +
            "and um.status = :status " +
            "order by um.id asc")
    Page<UserMission> findUserMissionsByStatus(
            @Param("userId") Long userId,
            @Param("status") MissionStatus status,
            Pageable pageable
    );

    //사용자가 완료한 미션 개수 조회
    @Query("select count(um) from UserMission um " +
            "where um.user.id = :userId and um.status = 'COMPLETED'")
    Long countCompletedMissions(@Param("userId") Long userId);
}
