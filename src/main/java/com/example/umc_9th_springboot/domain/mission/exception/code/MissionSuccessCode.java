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
            "미션 도전을 시작했습니다."),

    FOUND(HttpStatus.OK,
            "MISSION200_1",
                    "가게 미션 목록 조회에 성공했습니다."),

    FOUND_PROGRESS(HttpStatus.OK,
        "MISSION200_2",
                "진행 중인 미션 목록 조회 성공");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
