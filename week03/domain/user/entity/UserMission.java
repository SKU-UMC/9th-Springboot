package com.example.umc9th.domain.user.entity;

import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.global.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_mission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // user_mission_id

    private Boolean success;
    private LocalDateTime startAt;
    private LocalDateTime successAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user; //user_id

    @ManyToOne
    @JoinColumn(name = "mission_id")
    private Mission mission;
}
