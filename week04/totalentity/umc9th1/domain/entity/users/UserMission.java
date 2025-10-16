package com.example.umc9th1.domain.entity.users;


import com.example.umc9th1.domain.entity.mission.Mission;
import com.example.umc9th1.global.baseentity.BaseIdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import lombok.NonNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_mission")
public class UserMission extends BaseIdEntity {

    @Column(name = "success" , nullable = false,columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean success;

    @CreatedDate
    @Column(name = "executed_at",nullable = false,updatable = false)
    private LocalDateTime executedAt;

    @Column(name = "success_at")
    private LocalDateTime successAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    @JsonIgnore
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mission_id",nullable = false)
    private Mission mission;

    public UserMission(@NonNull Users user,
                       @NonNull Mission mission) {
        this.user = user;
        this.mission = mission;
    }
    public void updateSuccess() {
        this.success = true;
        this.successAt = LocalDateTime.now();
    }
}
