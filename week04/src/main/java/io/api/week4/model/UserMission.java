package io.api.week4.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_missions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MissionStatus missionStatus;

    @Column
    private LocalDateTime completedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private UserMission(User user, Mission mission) {
        this.user = user;
        this.mission = mission;
        this.missionStatus = MissionStatus.IN_PROGRESS;
        user.getUserMissions().add(this);
        mission.getUserMissions().add(this);
    }

    public static UserMission create(User user, Mission mission) {
        return new UserMission(user, mission);
    }

    public UserMission complete() {
        if (this.missionStatus == MissionStatus.COMPLETED) {
            throw new IllegalStateException("Mission is already completed");
        }
        this.missionStatus = MissionStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
        return this;
    }

    public boolean isCompleted() {
        return this.missionStatus == MissionStatus.COMPLETED;
    }

    public boolean isInProgress() {
        return this.missionStatus == MissionStatus.IN_PROGRESS;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public enum MissionStatus {
        IN_PROGRESS,
        COMPLETED
    }
}
