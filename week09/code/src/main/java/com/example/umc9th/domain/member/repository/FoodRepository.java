package com.example.umc9th.domain.member.repository;

import com.example.umc9th.domain.member.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
