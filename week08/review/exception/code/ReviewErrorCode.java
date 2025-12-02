package com.example.umc9th1.domain.review.exception.code;

import com.example.umc9th1.global.apipayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {
    //리뷰 생성 불가
    REVIEW_NOT_CREATED(HttpStatus.BAD_REQUEST , "REVIEW400_1","리뷰 형식이 올바르지 않습니다."),
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND , "REVIEW404_1","미션을 찾을 수 없습니다."),
    MISSION_ACCESS_DENIED(HttpStatus.FORBIDDEN , "REVIEW403_1","유저가 수행한 미션이 아닙니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "REVIEW400_2", "잘못된 요청입니다."),
    INVALID_STAR_RANGE(HttpStatus.BAD_REQUEST, "REVIEW400_3", "유효한 별점 범위가 아닙니다."),
    NO_REVIEWS_FOUND(HttpStatus.NOT_FOUND, "REVIEW404_2", "리뷰 검색 결과가 없습니다."),
    MISSION_ALREADY_CHALLENGED(HttpStatus.CONFLICT, "REVIEW409_1", "이미 도전 중인 미션입니다."),;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
