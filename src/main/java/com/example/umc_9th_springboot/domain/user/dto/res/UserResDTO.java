package com.example.umc_9th_springboot.domain.user.dto.res;
import com.example.umc_9th_springboot.global.auth.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResDTO {

    //회원가입 세션 방식 응답 DTO
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignUpDTO {
        private Long userId;
        private String email;
        private String name;
        private Role role;
    }

    //로그인 세션 방식 응답 DTO
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LoginSessionDTO {
        private Long userId;
        private String email;
        private String name;
        private Role role;
    }

    // 로그인 jwt 방식 응답 DTO
    @Builder
    public record LoginJwtDTO(
            Long userId,
            String accessToken
    ) {}

    // jwt 회원가입 응답 DTO
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignUpJwtDTO {
        private Long userId;
        private String email;
        private String name;
        private Role role;
        private String accessToken;
    }
}
