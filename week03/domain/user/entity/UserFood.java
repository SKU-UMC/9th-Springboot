package com.example.umc9th.domain.user.entity;

import com.example.umc9th.global.entity.Food;
import com.example.umc9th.global.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_food")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // user_food_id

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
