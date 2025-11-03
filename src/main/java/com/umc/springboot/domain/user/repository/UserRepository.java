package com.umc.springboot.domain.user.repository;

import com.umc.springboot.domain.user.entity.User;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  // 이메일로 사용자 단건 조회
  Optional<User> findByEmail(String email);

  // 전화번호로 사용자 단건 조회
  Optional<User> findByPhone(String phone);

  // 이름으로 사용자 단건 조회
  Optional<User> findByName(String name);

  // 이메일 중복 여부 확인
  boolean existsByEmail(String email);

  // 전화번호 중복 여부 확인
  boolean existsByPhone(String phone);

  // 이름과 생년월일을 함께 조건으로 조회
  @Query("SELECT u FROM User u WHERE u.name = :name AND u.birth = :birth")
  Optional<User> findByNameAndBirth(String name, LocalDate birth);
}