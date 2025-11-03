package com.umc.springboot.domain.store.repository;

import com.umc.springboot.domain.store.entity.District;
import com.umc.springboot.domain.store.entity.Store;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

  // 사업자번호로 단일 매장 조회
  Optional<Store> findByManagerNumber(String managerNumber);

  // 구(district)별 매장 목록 조회
  List<Store> findByDistrict(District district);

  // 상호명(name)으로 부분 검색
  List<Store> findByName(String keyword);
}