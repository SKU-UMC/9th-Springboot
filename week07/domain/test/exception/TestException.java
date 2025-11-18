package com.example.umc9th1.domain.test.exception;

import com.example.umc9th1.global.apipayload.code.BaseErrorCode;
import com.example.umc9th1.global.apipayload.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class TestException extends GeneralException {
    public TestException(BaseErrorCode code) {
        super(code);
    }
}
