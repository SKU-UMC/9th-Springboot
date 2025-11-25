package com.example.umc9th.domain.store.exception.code;

import com.example.umc9th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StoreErrorCode implements BaseErrorCode {

    REGION_NOT_EXIST(HttpStatus.BAD_REQUEST, "STORE400_1", "잘못된 요청입니다. 지역을 지정해주세요."),
    KEYWORD_NOT_EXIST(HttpStatus.BAD_REQUEST, "STORE400_2", "잘못된 요청입니다. 검색어를 입력해주세요."),
    SORT_NOT_FOUND(HttpStatus.BAD_REQUEST, "STORE400_3", "잘못된 요청입니다. 해당하는 정렬이 없습니다."),
    STORE_NOT_FOUND(HttpStatus.BAD_REQUEST, "STORE400_4", "해당하는 가게를 찾지 못했습니다."),

    ;




    private final HttpStatus status;
    private final String code;
    private final String message;
}
