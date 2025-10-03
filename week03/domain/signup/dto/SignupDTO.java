package com.example.umc9th.domain.signup.dto;

import com.example.umc9th.domain.enums.GenderType;
import com.example.umc9th.domain.enums.SocialType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupDTO {
    private String name;
    private GenderType gender; // "M" or "F"
    private LocalDate birth;
    private String detailAddress;
    private String email;
    private String phoneNumber;
    private Long locationId;
    private SocialType social; // "KAKAO", "NAVER", "FACEBOOK"
    private Integer point =0;


    private List<Long> termIds; // 선택된 약관
    private List<Long> foodIds; // 선택된 음식
}
