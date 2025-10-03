package com.example.umc9th.domain.signup.food.dto;
import com.example.umc9th.domain.enums.FoodType;
import lombok.Getter;

@Getter
public class FoodResponseDTO {
    private final Long foodId;
    private final FoodType foodName;

    public FoodResponseDTO(Long id, FoodType foodName) {
        this.foodId = id;
        this.foodName = foodName;
    }

}
