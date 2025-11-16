package com.umc.umc9th.domain.store.region.entity;

import com.umc.umc9th.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "region")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Long id;

    @Column(name = "region_name", nullable = false, length = 100)
    private String regionName;

    @OneToMany(mappedBy = "region")
    @Builder.Default
    private List<Store> stores = new ArrayList<>();
}
