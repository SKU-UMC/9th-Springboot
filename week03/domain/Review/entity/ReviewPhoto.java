package com.example.umc9th.domain.Review.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review_photo")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // review_photo_id

    private String reviewPhotoUrl;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

}
