package com.example.umc_9th_springboot.global.apiPayload.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

 //페이징 관련 에러 코드 정의

@Getter
@RequiredArgsConstructor
public enum PagingErrorCode implements BaseErrorCode {

    INVALID_PAGE(
            HttpStatus.BAD_REQUEST,
            "PAGING400_1",
            "page 값은 1 이상의 정수여야 합니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
