package com.example.umc9th1.domain.users.entity;

import com.example.umc9th1.domain.entity.others.Food;
import com.example.umc9th1.global.baseentity.BaseIdEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@Table(name = "user_food")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFood extends BaseIdEntity {
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    public UserFood(@NonNull Users user, @NonNull Food food) {
        this.user = user;
        this.food = food;
    }
}
