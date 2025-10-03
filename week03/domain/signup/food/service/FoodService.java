package com.example.umc9th.domain.signup.food.service;

import com.example.umc9th.domain.signup.food.dto.FoodResponseDTO;
import com.example.umc9th.domain.signup.food.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {
    private final FoodRepository foodRepository;
    @Autowired
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }
    // GET: 모든 음식 조회
    public List<FoodResponseDTO> getAllFoods() {
        return foodRepository.findAll()
                .stream()
                .map(f -> new FoodResponseDTO(f.getId(), f.getFoodName()))
                .collect(Collectors.toList());
    }

}
