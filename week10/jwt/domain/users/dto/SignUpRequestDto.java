package com.example.umc9th1.domain.users.dto;

import com.example.umc9th1.domain.enums.GenderType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {
    private String name;
    private GenderType gender;
    private LocalDate birth;
    private String email;
    private String password;
    private String detailAddress;
    private Long locationId;

    @Builder
    public SignUpRequestDto(String email,
                            String password,
                            String name,
                            GenderType gender,
                            LocalDate birth,
                            String detailAddress,
                            Long locationId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.detailAddress = detailAddress;
        this.locationId = locationId;
    }
}
