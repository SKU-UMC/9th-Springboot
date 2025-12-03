package com.example.umc_9th_springboot.domain.user.repository;

import com.example.umc_9th_springboot.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일로 조회
    Optional<User> findByEmail(String email);

    //마이페이지 회원 정보 조회
    Optional<User> findById(Long id);
}
