package com.example.umc9th.domain.mission.exception.code;

import com.example.umc9th.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    ADD_OK(HttpStatus.OK, "MISSION200_1", "미션이 정상적으로 추가되었습니다."),
    CHALLENGE_OK(HttpStatus.OK, "MISSION200_2", "미션 도전이 정상적으로 처리되었습니다."),

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
