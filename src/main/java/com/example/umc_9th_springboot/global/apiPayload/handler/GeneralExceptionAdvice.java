package com.example.umc_9th_springboot.global.apiPayload.handler;

import com.example.umc_9th_springboot.global.apiPayload.ApiResponse;
import com.example.umc_9th_springboot.global.apiPayload.code.BaseErrorCode;
import com.example.umc_9th_springboot.global.apiPayload.code.GeneralErrorCode;
import com.example.umc_9th_springboot.global.apiPayload.exception.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionAdvice {

    // 커스텀 예외 처리
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(GeneralException ex) {
        BaseErrorCode errorCode = ex.getCode();

        // 운영 중 디버깅을 위해 예외 로그 남기기
        log.warn("[GeneralException] errorCode: {}, message: {}",
                errorCode, ex.getMessage(), ex);

        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.onFailure(errorCode, null));
    }

    // 예상치 못한 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleOtherExceptions(Exception ex) {
        BaseErrorCode errorCode = GeneralErrorCode.INTERNAL_SERVER_ERROR;

        // 500 에러 등 치명적인 예외는 error 로그 남기기
        log.error("[UnhandledException] message: {}", ex.getMessage(), ex);

        // 클라이언트에는 안전한 에러 메시지만 전달
        String safeMessage = "서버 내부 오류가 발생했습니다.";

        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.onFailure(errorCode, safeMessage));
    }
}
