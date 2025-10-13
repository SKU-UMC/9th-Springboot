package io.api.week4.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "missions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    private Integer rewardPoints;

    @Column
    private LocalDateTime deadline;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
    private List<MissionReview> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
    private List<UserMission> userMissions =  new ArrayList<>();

    private Mission(String title, String description, Integer rewardPoints, LocalDateTime deadline) {
        this.title = title;
        this.description = description;
        this.rewardPoints = rewardPoints;
        this.deadline = deadline;
    }

    public static Mission createMission(String title, String description, Integer rewardPoints, LocalDateTime deadline) {
        return new Mission(title, description, rewardPoints, deadline);
    }

    public Mission update(String title, String description, Integer rewardPoints, LocalDateTime deadline) {
        this.title = title;
        this.description = description;
        this.rewardPoints = rewardPoints;
        this.deadline = deadline;
        return this;
    }
}
