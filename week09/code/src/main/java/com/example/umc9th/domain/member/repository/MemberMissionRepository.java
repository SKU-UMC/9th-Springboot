package com.example.umc9th.domain.member.repository;

import com.example.umc9th.domain.member.entity.mapping.MemberMission;
import com.example.umc9th.domain.mission.entity.Mission;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM MemberMission mm WHERE mm.member.id = :memberId")
    void deleteAllByMemberIdInBatch(@Param("memberId") Long memberId);

    //3. 진행 중, 진행 완료 미션
    @Query(value = "SELECT mm FROM MemberMission mm " +
            "JOIN FETCH mm.mission m " +
            "JOIN FETCH m.store " +
            "WHERE mm.member.id = :memberId ",
            countQuery = "SELECT count(mm) FROM MemberMission mm WHERE mm.member.id = :memberId")   //countQuery 분리(페이징)
    Page<MemberMission> findMemberMissionsByMemberIdWithDetails(Long memberId, Pageable pageable);

    //4-1. 홈 화면 - 해당 지역 달성 미션 개수
    @Query("SELECT COUNT(mm) FROM MemberMission mm " +
            "WHERE mm.member.id = :memberId AND mm.mission.store.location.id = :locationId")
    Long countCompletedMissionsInLocation(Long memberId, Long locationId);





}
