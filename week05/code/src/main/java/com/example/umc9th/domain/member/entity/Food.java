package com.example.umc9th.domain.member.entity;

import com.example.umc9th.domain.member.entity.mapping.MemberFood;
import com.example.umc9th.domain.member.enums.FoodName;
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
@Table(name = "food")
public class Food extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private FoodName name = FoodName.NONE;

    @OneToMany(mappedBy = "food")
    private Set<MemberFood> memberFoodList = new HashSet<>();
}
