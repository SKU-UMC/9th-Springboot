package com.example.umc_9th_springboot.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GeneralSuccessCode implements BaseSuccessCode {

    OK(HttpStatus.OK,
            "COMMON200",
            "성공적으로 요청을 처리했습니다."),
    CREATED(HttpStatus.CREATED,
            "COMMON201",
            "성공적으로 생성되었습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT,
            "COMMON204",
            "성공적으로 처리되었으며, 반환할 내용이 없습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
