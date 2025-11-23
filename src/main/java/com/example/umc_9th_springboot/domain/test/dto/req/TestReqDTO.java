package com.example.umc_9th_springboot.domain.test.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class TestReqDTO {

    @Getter
    @NoArgsConstructor
    public static class TestFlagReq {
        private Long flag;
    }
}
