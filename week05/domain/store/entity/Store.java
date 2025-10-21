package com.example.umc9th1.domain.store.entity;

import com.example.umc9th1.domain.entity.others.Location;
import com.example.umc9th1.domain.review.entity.Review;
import com.example.umc9th1.global.baseentity.BaseIdAndTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseIdAndTimeEntity {

    @Column(name = "store_name" , nullable = false ,length = 100)
    private String name;

    @Column(name = "store_code",nullable = false,unique = true)
    private Long storeCode;

    @Column(name = "detail_address",nullable = false , length = 254)
    private String detailAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_category_id" , nullable = false)
    private StoreCategory storeCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id",nullable = false)
    private Location location;

    @OneToMany(mappedBy = "store" , cascade = CascadeType.REMOVE)
    private List<Review> reviewList = new ArrayList<>();


    public Store(@NonNull String name,
                 @NonNull Long storeCode,
                 @NonNull String detailAddress,
                 @NonNull StoreCategory storeCategory,
                 @NonNull Location location) {
        this.name = name;
        this.storeCode = storeCode;
        this.detailAddress = detailAddress;
        this.storeCategory = storeCategory;
        this.location = location;
    }



}
