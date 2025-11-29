package com.example.umc_9th_springboot.domain.mission.exception.code;

import com.example.umc_9th_springboot.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    CHALLENGE_CREATED(HttpStatus.OK,
            "MISSION200_1",
            "미션 도전을 시작했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
