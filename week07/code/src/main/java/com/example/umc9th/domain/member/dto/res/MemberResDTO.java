package com.example.umc9th.domain.member.dto.res;

import lombok.Builder;
import lombok.Getter;

public class MemberResDTO {

    @Getter
    @Builder
    public static class Delete{
        private Long memeberId;

    }

}
