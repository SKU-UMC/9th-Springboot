package com.example.umc9th.global.entity;

import com.example.umc9th.domain.enums.FoodType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "food")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // food_id

    @Enumerated(EnumType.STRING)
    private FoodType foodName;

}
