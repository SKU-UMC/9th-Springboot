package com.example.umc_9th_springboot.domain.test.exception;

import com.example.umc_9th_springboot.global.apiPayload.code.BaseErrorCode;
import com.example.umc_9th_springboot.global.apiPayload.exception.GeneralException;

public class TestException extends GeneralException {

    public TestException(BaseErrorCode code) {
        super(code);
    }
}
