package com.example.umc9th.domain.mission.entity;

import com.example.umc9th.domain.member.entity.mapping.MemberMission;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "mission")
public class Mission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="deadline", nullable = false)
    private LocalDate deadline;

    @Column(name="mission_condition", nullable = false)
    private String missionCondition;

    @Column(name="point", nullable = false)
    private Integer point;

    @OneToMany(mappedBy = "mission")
    private Set<MemberMission> memberMissionList = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mission)) return false;
        Mission mission = (Mission) o;
        return Objects.equals(getStore(), mission.getStore());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getStore());
    }
}
