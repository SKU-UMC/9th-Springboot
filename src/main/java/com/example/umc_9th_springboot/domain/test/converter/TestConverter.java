package com.example.umc_9th_springboot.domain.test.converter;

import com.example.umc_9th_springboot.domain.test.dto.res.TestResDTO;

public class TestConverter {

    // 객체 -> DTO (정상 응답)
    public static TestResDTO.Testing toTestingDTO(String testing) {
        return TestResDTO.Testing.builder()
                .testString(testing)
                .build();
    }

    // 객체 -> DTO (예외 상황 응답용)
    public static TestResDTO.Exception toExceptionDTO(String testing) {
        return TestResDTO.Exception.builder()
                .testString(testing)
                .build();
    }
}
