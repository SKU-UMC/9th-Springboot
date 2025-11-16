package com.umc.umc9th.domain.mission.entity;

import com.umc.umc9th.domain.mission.userMission.entity.UserMission;
import com.umc.umc9th.domain.store.entity.Store;
import com.umc.umc9th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "mission")
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    @Column(name = "mission_point", nullable = false)
    private Long missionPoint;

    @Column(name = "mission_code", nullable = false, length = 32)
    private String missionCode;

    @Column(name = "mission_cost", nullable = false)
    private Long missionCost;

    @Column(name = "mission_due", nullable = false)
    private Integer missionDue;

    @Enumerated(EnumType.STRING)
    @Column(name = "mission_status", nullable = false, length = 32)
    private MissionStatus missionStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Builder.Default
    @OneToMany(mappedBy = "mission", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserMission> userMissions = new ArrayList<>();
}