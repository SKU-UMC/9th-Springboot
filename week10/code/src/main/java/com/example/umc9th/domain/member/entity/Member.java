package com.example.umc9th.domain.member.entity;

import com.example.umc9th.domain.member.entity.mapping.MemberFood;
import com.example.umc9th.domain.member.entity.mapping.MemberTerm;
import com.example.umc9th.domain.member.enums.Address;
import com.example.umc9th.domain.member.enums.Gender;
import com.example.umc9th.domain.member.entity.mapping.MemberMission;
import com.example.umc9th.domain.member.enums.SocialType;
import com.example.umc9th.domain.review.entity.Reply;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.global.auth.enums.Role;
import com.example.umc9th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", length = 3)
    private String name;

    @Column(name="gender")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Gender gender = Gender.NONE;

    @Column(name="birth")
    private LocalDate birth;

    @Column(name="password")
    private String password;

    @Column(name="address")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Address address = Address.NONE;

    @Column(name="detail_address")
    private String detailAddress;

    @Column(name="point")
    @Builder.Default
    private Integer point = 0;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    //소셜 로그인
    @Column(unique = true)
    private Long socialUid;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;
    //

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name = "delete_at")
    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private Set<MemberFood> memberFoodList = new HashSet<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private Set<MemberTerm> memberTermList = new HashSet<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private Set<MemberMission> memberMissionList = new HashSet<>();

    @OneToMany(mappedBy = "member")
    private Set<Review> memberReviewList = new HashSet<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private RefreshToken refreshToken;


}
