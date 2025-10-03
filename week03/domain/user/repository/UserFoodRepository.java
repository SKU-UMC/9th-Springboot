package com.example.umc9th.domain.user.repository;

import com.example.umc9th.domain.user.entity.UserFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFoodRepository extends JpaRepository<UserFood,Long> {
}
