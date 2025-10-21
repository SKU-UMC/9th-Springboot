package com.example.umc9th1.domain.mission.repository;

import com.example.umc9th1.domain.mission.entity.Mission;
import com.example.umc9th1.domain.users.dto.UserHomeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {


    @Query("""
    SELECT new com.example.umc9th1.domain.users.dto.UserHomeDto(
            r.address,
            (SELECT COUNT(um)
             FROM UserMission um
             WHERE um.user.id = u.id AND um.success = true),
            s.storeCategory,
            m.id,
            s.name,
            m.conditions,
            m.point,
            m.endDate,
            m.createAt
        )
        FROM Users u
        JOIN u.location r
        JOIN Store s ON s.location.id = r.id
        JOIN Mission m ON m.store.id = s.id
        WHERE u.id = :userId
""")
    Page<UserHomeDto> findMissionsByUserId( @Param("userId") Long userId, Pageable pageable);




}
