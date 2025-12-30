package com.example.umc9th1.domain.users.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {
    private String name;
    private String email;
    private String password;
    private Long locationId; // 위치 ID

    @Builder
    public SignUpRequestDto(String name, String email, String password, Long locationId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.locationId = locationId;
    }
}
