package com.umc.umc9th.domain.user.entity;

import com.umc.umc9th.domain.address.entity.Address;
import com.umc.umc9th.domain.mission.userMission.entity.UserMission;
import com.umc.umc9th.domain.question.entity.Question;
import com.umc.umc9th.domain.review.entity.Review;
import com.umc.umc9th.domain.review.reviewComment.entity.ReviewComment;
import com.umc.umc9th.domain.user.userPreferenceCuisine.entity.UserPreferenceCuisine;
import com.umc.umc9th.domain.user.userTerm.entity.UserTerm;
import com.umc.umc9th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name", nullable = false, length = 100)
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_gender", nullable = false, length = 20)
    private Gender userGender;

    @Column(name = "user_birth")
    private LocalDateTime userBirth;

    @Column(name = "user_point", nullable = false)
    private Long userPoint;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_social", nullable = false)
    private Boolean isSocial;

    @Column(name = "social_id", length = 100)
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_social_provider", length = 30)
    private SocialProvider userSocialProvider;

    @Column(name = "user_profile_url", length = 512)
    private String userProfileUrl;

    @Column(name = "user_nickname", length = 50)
    private String userNickname;

    @Column(name = "user_email", length = 255)
    private String userEmail;

    @Column(name = "user_phone", length = 16)
    private String userPhone;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "address_id", unique = true)
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<UserTerm> userTerms = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<UserPreferenceCuisine> userPreferenceCuisines = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<ReviewComment> reviewComments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserMission> userMissions = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Question> questions = new ArrayList<>();
}