package com.example.umc9th.domain.Review.entity;

import com.example.umc9th.global.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review_comment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // review_comment_id

    private String content;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

}
