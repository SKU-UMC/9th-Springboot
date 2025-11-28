// com.umc.umc9th.domain.mission.userMission.UserMissionRepository
package com.umc.umc9th.domain.mission.userMission.repository;

import com.umc.umc9th.domain.mission.dto.response.AvailableMissionInRegionResponse;
import com.umc.umc9th.domain.mission.dto.response.RegionMissionSuccessCountResponse;
import com.umc.umc9th.domain.mission.entity.Mission;
import com.umc.umc9th.domain.mission.userMission.dto.response.UserMissionInProgressResponse;
import com.umc.umc9th.domain.mission.userMission.dto.response.UserMissionSuccessResponse;
import com.umc.umc9th.domain.mission.userMission.entity.UserMission;
import com.umc.umc9th.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    // 미션 탭 - 진행중 미션 (남은 일수 포함)
    @Query(value = """
select *
from user_mission um
where um.user_id = :userId
  and um.mission_status = 'IN_PROGRESS'
  and timestampdiff(second, um.created_at, now()) < :limit
""",
            countQuery = """
select count(*)
from user_mission um
where um.user_id = :userId
  and um.mission_status = 'IN_PROGRESS'
  and timestampdiff(second, um.created_at, now()) < :limit
""",
            nativeQuery = true)
    Page<UserMission> findInProgressMissions(@Param("userId") Long userId,
                                             @Param("limit") long limit,
                                             Pageable pageable);


    // 미션 탭 -  진행 완료 미션
    @Query("""
        select new com.umc.umc9th.domain.mission.userMission.dto.response.UserMissionSuccessResponse(
            um.id,
            um.missionStatus,
            um.createdAt,
            m.id,
            m.missionPoint,
            m.missionCost,
            s.id,
            s.storeName
        )
        from UserMission um
        join um.mission m
        left join m.store s
        where um.user.id = :userId
          and um.missionStatus = com.umc.umc9th.domain.mission.entity.MissionStatus.SUCCESS
        order by um.createdAt desc, um.id desc
    """)
    Page<UserMissionSuccessResponse> findSuccessMissions(
            @Param("userId") Long userId,
            Pageable pageable
    );

    // 홈화면 - 지역별 '내 성공 미션 수'
    @Query("""
        select new com.umc.umc9th.domain.mission.dto.response.RegionMissionSuccessCountResponse(
            r.id,
            r.regionName,
            count(um.id)
        )
        from UserMission um
            join um.mission m
            join m.store s
            join s.region r
        where um.user.id = :userId
          and r.id = :regionId
          and um.missionStatus = com.umc.umc9th.domain.mission.entity.MissionStatus.SUCCESS
        group by r.id, r.regionName
    """)
    Optional<RegionMissionSuccessCountResponse> countMySuccessMissionsInRegion(
            @Param("userId") Long userId,
            @Param("regionId") Long regionId
    );

    // 홈화면 - 해당 지역에서 내가 '도전 가능(NOT_YET)'한 미션 목록 (페이징)
    @Query("""
        select new com.umc.umc9th.domain.mission.dto.response.AvailableMissionInRegionResponse(
            m.id,
            m.missionPoint,
            m.missionCost,
            s.id,
            s.storeName,
            m.missionDue
        )
        from UserMission um
            join um.mission m
            join m.store s
        where um.user.id = :userId
          and s.region.id = :regionId
          and um.missionStatus = com.umc.umc9th.domain.mission.entity.MissionStatus.NOT_YET
        order by m.createdAt desc, m.id desc
    """)
    Page<AvailableMissionInRegionResponse> findAvailableMissionsInRegion(
            @Param("userId") Long userId,
            @Param("regionId") Long regionId,
            Pageable pageable
    );


    Boolean existsByUserAndMission(User user, Mission mission);
}
