package com.example.umc_9th_springboot.domain.test.exception.code;

import com.example.umc_9th_springboot.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TestErrorCode implements BaseErrorCode {

    // For test
    TEST_EXCEPTION(HttpStatus.BAD_REQUEST, "TEST400_1", "이거는 테스트 에러입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
