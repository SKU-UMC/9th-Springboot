package com.example.umc9th.global.entity;

import com.example.umc9th.domain.enums.GenderType;
import com.example.umc9th.domain.enums.SocialType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // user_id

    private String name;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    private LocalDateTime deletedAt;
    private LocalDate birth;
    private String detailAddress;

    @Enumerated(EnumType.STRING)
    private SocialType social;

    private Integer point;

    private String phoneNum;
    private String email;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Region location;
}
