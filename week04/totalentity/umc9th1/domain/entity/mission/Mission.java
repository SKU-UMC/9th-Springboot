package com.example.umc9th1.domain.entity.mission;

import com.example.umc9th1.domain.entity.store.Store;
import com.example.umc9th1.global.baseentity.BaseIdAndTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "mission")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mission extends BaseIdAndTimeEntity {

    @Column(name = "point",nullable = false)
    private int point;

    @Column(name = "end_date",nullable = false)
    private LocalDate endDate;

    @Column(name = "conditions",nullable = false , length = 254)
    private String conditions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id" , nullable = false)
    private Store store;


    public Mission( int point,
                   @NonNull LocalDate endDate,
                   @NonNull String conditions,
                   @NonNull Store store) {
        this.point = point;
        this.endDate = endDate;
        this.conditions = conditions;
        this.store = store;
    }


}
