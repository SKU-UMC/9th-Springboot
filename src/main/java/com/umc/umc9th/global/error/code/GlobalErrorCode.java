package com.umc.umc9th.global.error.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements ErrorCode {

    /// 200 : 요청 성공
    SUCCESS(HttpStatus.OK, "SUCCESS", "요청에 성공했습니다."),
    CREATED(HttpStatus.CREATED, "CREATED", "리소스가 성공적으로 생성되었습니다."),

    /// 400 : 클라이언트 오류
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "REQUEST_001", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "REQUEST_002", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "REQUEST_003", "접근 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "REQUEST_004", "리소스를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "REQUEST_005", "허용되지 않는 HTTP 메서드입니다."),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "REQUEST_006", "지원하지 않는 Media Type 요청입니다."),
    REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "REQUEST_007", "요청 시간이 초과되었습니다."),

    /// 500 : 서버 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER_001", "서버 내부 오류가 발생했습니다."),
    BAD_GATEWAY(HttpStatus.BAD_GATEWAY, "SERVER_002", "잘못된 게이트웨이 응답이 수신되었습니다."),


    /// 토큰 관련 오류
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_001", "유효하지 않은 토큰입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_002", "Refresh Token이 일치하지 않습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_003", "토큰이 만료되었습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

}