package com.example.umc_9th_springboot.domain.user.dto.req;

import com.example.umc_9th_springboot.domain.user.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

public class UserReqDTO {

    //회원가입 세션 방식 요청 DTO
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignUpDTO {
        private String email;
        private String password;
        private String name;
        private Gender gender;
        private String birth;
        private String address;
    }

    //로그인 세션 방식 요청 DTO
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LoginSessionDTO {
        private String email;
        private String password;
    }

    // 로그인 jwt 방식 요청 DTO
    public record LoginJwtDTO(
            @NotBlank
            String email,
            @NotBlank
            String password
    ) {}
}
