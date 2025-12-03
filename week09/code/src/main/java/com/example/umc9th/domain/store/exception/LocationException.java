package com.example.umc9th.domain.store.exception;

import com.example.umc9th.global.apiPayload.code.BaseErrorCode;
import com.example.umc9th.global.apiPayload.exception.GeneralException;

public class LocationException extends GeneralException {
    public LocationException(BaseErrorCode code) {
        super(code);
    }
}
