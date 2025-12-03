package com.example.umc9th.domain.mission.exception.code;

import com.example.umc9th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    NOT_FOUND(HttpStatus.BAD_REQUEST, "MISSION400_1", "존재하지 않는 미션입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
