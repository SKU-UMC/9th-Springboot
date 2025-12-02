package com.example.umc9th1.domain.review.exception;

import com.example.umc9th1.global.apipayload.code.BaseErrorCode;
import com.example.umc9th1.global.apipayload.exception.GeneralException;

public class ReviewException extends GeneralException {
    public ReviewException(BaseErrorCode code) {
        super(code);
    }
}
