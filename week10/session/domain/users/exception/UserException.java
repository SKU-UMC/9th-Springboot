package com.example.umc9th1.domain.users.exception;

import com.example.umc9th1.global.apipayload.code.BaseErrorCode;
import com.example.umc9th1.global.apipayload.exception.GeneralException;

public class UserException extends GeneralException {
    public UserException(BaseErrorCode code) {
        super(code);
    }
}
