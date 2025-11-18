package com.example.umc9th1.domain.test.converter;

import com.example.umc9th1.domain.test.dto.TestResDto;
import com.example.umc9th1.global.apipayload.code.BaseSuccessCode;

public class TestConverter {

    public static TestResDto.Testing toTestResDto(String testString) {
        return TestResDto.Testing.builder()
                .testString(testString)
                .build();
    }

    public static TestResDto.Exception toExceptionDto(String testString) {
        return TestResDto.Exception.builder().testString(testString).build();
    }
}
