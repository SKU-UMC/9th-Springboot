package com.example.umc9th1.domain.test.exception.code;

import com.example.umc9th1.domain.test.exception.TestException;
import com.example.umc9th1.global.apipayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TestErrorCode implements BaseErrorCode {

    TEST_EXCEPTION(HttpStatus.BAD_REQUEST , "TEST400_1","이거는 테스트");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
