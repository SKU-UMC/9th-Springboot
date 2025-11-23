package com.example.umc_9th_springboot.domain.test.dto.res;

import lombok.Builder;
import lombok.Getter;

public class TestResDTO {

    @Getter
    @Builder
    public static class Testing {
        private String testString;
    }

    @Getter
    @Builder
    public static class Exception {
        private String testString;
    }
}
