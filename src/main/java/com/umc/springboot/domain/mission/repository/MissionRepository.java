package com.umc.springboot.domain.mission.repository;

import com.umc.springboot.domain.mission.entity.Mission;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {

  // 특정 store의 미션 목록 조회
  List<Mission> findByStoreId(Long storeId);

  // 마감일이 특정 날짜 이후인 미션 조회
  List<Mission> findByDeadlineAfter(LocalDate date);

  // 특정 날짜에 등록된 미션 조회
  List<Mission> findByDate(LocalDate date);
}