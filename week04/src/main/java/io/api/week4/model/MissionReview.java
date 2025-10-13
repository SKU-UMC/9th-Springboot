package io.api.week4.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mission_reviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer rating;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private MissionReview(Integer rating, String content, Mission mission, User user) {
        this.rating = rating;
        this.content = content;
        this.mission = mission;
        this.user = user;
        mission.getReviews().add(this);
        user.getReviews().add(this);
    }

    public static MissionReview createMissionReview(Integer rating, String content, Mission mission, User user) {
        return new MissionReview(rating, content, mission, user);
    }

    public MissionReview update(Integer rating, String content) {
        this.rating = rating;
        this.content = content;
        return this;
    }
}
