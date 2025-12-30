package com.example.umc9th1.domain.users.repository;

import com.example.umc9th1.domain.users.dto.UserCompletedMissionDto;
import com.example.umc9th1.domain.users.dto.UserOngoingMissionDto;
import com.example.umc9th1.domain.users.entity.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    //진행중
    @Query("""
            SELECT new com.example.umc9th1.domain.users.dto.UserOngoingMissionDto(
            m.id, m.conditions, m.point, s.name,um.executedAt)
            FROM UserMission um
            JOIN um.mission m
            JOIN m.store s
            WHERE um.user.id = :userId AND um.success = false
            """)
    Page<UserOngoingMissionDto> findUnsuccessfulMissionsByUserId(@Param("userId") Long userId , Pageable pageable);


    //진행 완료
    @Query("""
            SELECT new com.example.umc9th1.domain.users.dto.UserCompletedMissionDto(
            m.id, m.conditions, m.point, s.name,um.successAt)
            FROM UserMission um
            JOIN um.mission m
            JOIN m.store s
            WHERE um.user.id = :userId AND um.success = true
            """)
    Page<UserCompletedMissionDto> findSuccessfulMissionsByUserId(@Param("userId") Long userId , Pageable pageable);

    //단일 유저 미션 체크
    Optional<UserMission> findByUserIdAndMissionId(Long userId, Long missionId);

    boolean existsByUserIdAndMissionId(Long userId, Long missionId);
}
