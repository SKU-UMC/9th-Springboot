package com.example.umc9th.domain.user.repository;

import com.example.umc9th.domain.user.entity.UserTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTermRepository extends JpaRepository<UserTerm,Long> {
}
