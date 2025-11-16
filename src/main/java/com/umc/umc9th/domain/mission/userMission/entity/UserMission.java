package com.umc.umc9th.domain.mission.userMission.entity;

import com.umc.umc9th.domain.mission.entity.Mission;
import com.umc.umc9th.domain.mission.entity.MissionStatus;
import com.umc.umc9th.domain.user.entity.User;
import com.umc.umc9th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_mission")
public class UserMission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_mission_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "mission_status", nullable = false, length = 32)
    private MissionStatus missionStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;
}
