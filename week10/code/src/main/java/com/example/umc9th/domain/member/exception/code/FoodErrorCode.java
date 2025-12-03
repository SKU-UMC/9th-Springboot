package com.example.umc9th.domain.member.exception.code;

import com.example.umc9th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FoodErrorCode implements BaseErrorCode {
    NOT_FOUND(HttpStatus.BAD_REQUEST, "FOOD400_1", "존재하지 않는 음식입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
