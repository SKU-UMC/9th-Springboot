package com.example.umc9th1.domain.users.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpResponseDto {

    private String email;
    private String name;
    @Builder
    public SignUpResponseDto(String email, String password, String name) {
        this.email = email;
        this.name = name;
    }
}
