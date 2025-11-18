package com.example.umc9th1.domain.test.dto;

import lombok.Builder;
import lombok.Getter;

public class TestResDto {

    @Builder
    @Getter
    public static class Testing {
        private String testString;
    }
    @Builder
    @Getter
    public static class Exception{
        private String testString;
    }

}
