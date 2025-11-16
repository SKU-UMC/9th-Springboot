package com.umc.umc9th.domain.cuisine.entity;

import com.umc.umc9th.domain.user.userPreferenceCuisine.entity.UserPreferenceCuisine;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "cuisine")
public class Cuisine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuisine_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "cuisine_type", nullable = false, length = 50)
    private CuisineType cuisineType;

    @OneToMany(mappedBy = "cuisine", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<UserPreferenceCuisine> userPreferenceCuisines = new ArrayList<>();
}
