package com.example.umc9th1.domain.users.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {

    private String accessToken;

    @Builder
    public LoginResponseDto( String accessToken) {
        this.accessToken = accessToken;
    }
}
