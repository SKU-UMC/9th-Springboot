package com.example.umc9th.domain.member.dto.res;

import com.example.umc9th.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class MemberResDTO {

    @Getter
    @Builder
    public static class Delete{
        private Long memeberId;

    }

    @Builder
    public record JoinDTO(
            Long memberId,
            LocalDateTime createAt
    ){}

    // 로그인
    @Builder
    public record LoginDTO(
            Long memberId
    ){
        public static LoginDTO of(Member member) {
            return LoginDTO.builder()
                    .memberId(member.getId())
                    .build();
        }
    }

    @Builder
    public record LoginTokenDTO(
            Member member,
            String accessToken,
            String refreshToken
    ){
        public static LoginTokenDTO of(Member member, String accessToken, String refreshToken) {
            return LoginTokenDTO.builder()
                    .member(member)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
    }

    @Builder
    public record ReissueDTO(
            String accessToken
    ){
        public static ReissueDTO of(String accessToken){
            return ReissueDTO.builder()
                    .accessToken(accessToken)
                    .build();
        }
    }

}
