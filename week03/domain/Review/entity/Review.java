package com.example.umc9th.domain.Review.entity;

import com.example.umc9th.global.entity.BaseTimeEntity;
import com.example.umc9th.global.entity.Store;
import com.example.umc9th.global.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // review_id

    private double star;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    // 커스텀 생성자
    public Review(Users user, Store store, double star, String content) {
        this.user = user;
        this.store = store;
        this.star = star;
        this.content = content;
    }
}
