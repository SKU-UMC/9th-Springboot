package com.umc.umc9th.domain.store.entity;

import com.umc.umc9th.domain.address.entity.Address;
import com.umc.umc9th.domain.cuisine.entity.CuisineType;
import com.umc.umc9th.domain.mission.entity.Mission;
import com.umc.umc9th.domain.review.entity.Review;
import com.umc.umc9th.domain.store.region.entity.Region;
import com.umc.umc9th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "store")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(name = "store_name", nullable = false, length = 120)
    private String storeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "cuisine_type", nullable = false, length = 30)
    private CuisineType cuisineType;

    @Column(name = "store_rating", nullable = false)
    private Double storeRating;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "address_id", unique = true)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<Mission> missions = new ArrayList<>();

}