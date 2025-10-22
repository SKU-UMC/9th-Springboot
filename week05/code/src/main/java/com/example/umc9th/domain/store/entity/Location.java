package com.example.umc9th.domain.store.entity;

import com.example.umc9th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "location")
public class Location extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",  nullable = false)
    private String name;

    @OneToMany(mappedBy = "location")
    private Set<Store> storeList = new HashSet<>();
}
