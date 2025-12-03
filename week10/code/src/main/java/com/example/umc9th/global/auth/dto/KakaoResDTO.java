package com.example.umc9th.global.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KakaoResDTO {

    // 액세스 토큰 응답 DTO
    public record TokenDTO(
            @JsonProperty("access_token")
            String accessToken,

            @JsonProperty("refresh_token")
            String refreshToken,

            @JsonProperty("expires_in")
            Integer expiresIn,

            @JsonProperty("refresh_token_expires_in")
            Integer refreshTokenExpiresIn,

            @JsonProperty("token_type")
            String tokenType,

            @JsonProperty("scope")
            String scope
    ) {}

    // 사용자 정보 응답 DTO
    public record UserInfoDTO(
            Long id, // 카카오 회원 번호

            @JsonProperty("connected_at")
            String connectedAt,

            @JsonProperty("kakao_account")
            KakaoAccount kakaoAccount
    ) {

        public record KakaoAccount(
                @JsonProperty("email_needs_agreement")
                Boolean emailNeedsAgreement,

                @JsonProperty("has_email")
                Boolean hasEmail,

                String email
        ) {}
    }
}
