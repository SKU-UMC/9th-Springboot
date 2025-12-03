package com.example.umc9th.domain.store.exception.code;

import com.example.umc9th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LocationErrorCode implements BaseErrorCode {

    LOCATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "LOCATION400_1", "존재하지 않는 지역입니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
