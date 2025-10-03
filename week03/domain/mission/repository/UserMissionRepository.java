package com.example.umc9th.domain.mission.repository;

import com.example.umc9th.domain.mission.dto.MissionDTO;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.user.entity.UserMission;
import com.example.umc9th.global.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserMissionRepository extends CrudRepository<UserMission,Long> {

    //미션진행중
    @Query("SELECT new com.example.umc9th.domain.mission.dto.MissionDTO(" +
            "m.id, " +
            "s.storeName, " +
            "m.point, " +
            "m.condition, " +
            "um.startAt) " +
            "FROM UserMission um " +
            "JOIN um.mission m " +
            "JOIN m.store s " +
            "WHERE um.user.id = :userId " +
            "AND um.success = false " +
            "AND (:lastCreateAt IS NULL OR m.createdAt > :lastCreateAt " +
            "     OR (m.createdAt = :lastCreateAt AND m.id > :lastMissionId)) " +
            "ORDER BY m.createdAt ASC, m.id ASC")
    List<MissionDTO> findOngoingMissions(
            @Param("userId") Long userId,
            @Param("lastCreateAt") LocalDateTime lastCreateAt,
            @Param("lastMissionId") Long lastMissionId
    );

    //미션완료
    @Query("SELECT new com.example.umc9th.domain.mission.dto.MissionDTO(" +
            "m.id, " +
            "s.storeName, " +
            "m.point, " +
            "m.condition, " +
            "um.startAt) " +
            "FROM UserMission um " +
            "JOIN um.mission m " +
            "JOIN m.store s " +
            "WHERE um.user.id = :userId " +
            "AND um.success = true " +
            "AND (:lastCreateAt IS NULL OR m.createdAt < :lastCreateAt " +
            "     OR (m.createdAt = :lastCreateAt AND m.id < :lastMissionId)) " +
            "ORDER BY m.createdAt DESC, m.id DESC")
    List<MissionDTO> findCompletedMissions(
            @Param("userId") Long userId,
            @Param("lastCreateAt") LocalDateTime lastCreateAt,
            @Param("lastMissionId") Long lastMissionId
    );

    //성공버튼(특정 유저의 특정 미션아이디)
    Optional<UserMission> findByUserIdAndMissionIdAndSuccessFalse(Long userId, Long missionId);
    //성공완료된 user만 리뷰작성가능
    Optional<UserMission> findByUserIdAndMissionIdAndSuccessTrue(Long userId,Long missionId);


}
