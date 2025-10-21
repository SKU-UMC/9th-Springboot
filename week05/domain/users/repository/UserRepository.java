package com.example.umc9th1.domain.users.repository;

import com.example.umc9th1.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
}
