package com.example.umc9th.domain.home.repository;

import com.example.umc9th.domain.home.dto.HomeMissionDTO;
import com.example.umc9th.domain.mission.entity.Mission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HomeRepository extends CrudRepository<Mission, Long> {

    @Query("SELECT new com.example.umc9th.domain.home.dto.HomeMissionDTO(" +
            "r.address, " +
            "(SELECT COUNT(um) * 1L FROM UserMission um WHERE um.user.id = :userId AND um.success = true), " +
            "asStore.abstractStoreName, " +
            "m.id, " +
            "s.storeName, " +
            "m.condition, " +
            "m.point, " +
            "m.endDate, " +
            "m.createdAt) " +
            "FROM Mission m " +
            "JOIN m.store s " +
            "JOIN s.abstractStore asStore " +
            "JOIN s.location r " +
            "WHERE (:lastCreateAt IS NULL OR m.createdAt < :lastCreateAt " +
            "     OR (m.createdAt = :lastCreateAt AND m.id < :lastMissionId)) " +
            "ORDER BY m.createdAt DESC, m.id DESC")
    List<HomeMissionDTO> findHomeMissions(
            @Param("userId") Long userId,
            @Param("lastCreateAt") LocalDateTime lastCreateAt,
            @Param("lastMissionId") Long lastMissionId
    );
}
