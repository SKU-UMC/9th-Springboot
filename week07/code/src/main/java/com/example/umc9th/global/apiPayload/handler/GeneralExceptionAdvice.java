package com.example.umc9th.global.apiPayload.handler;

import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.BaseErrorCode;
import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.global.apiPayload.exception.GeneralException;
import com.example.umc9th.global.service.DiscordWebhookService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GeneralExceptionAdvice {

    private final DiscordWebhookService discordWebhookService;

    // 애플리케이션에서 발생하는 커스텀 예외를 처리
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Void>> handleException(GeneralException ex){
        return ResponseEntity.status(ex.getCode().getStatus())
                .body(ApiResponse.onFailure(ex.getCode(), null));
    }

    // 그 외의 정의되지 않은 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception ex, HttpServletRequest request){

        // 오류난 url과 httpMethod 가져오기
        String url = request.getRequestURI();
        String method = request.getMethod();

        // 로그 출력
        log.error("[500 ERROR] {} {} - {}", method, url, ex.getMessage(), ex);

        // 웹훅으로 전송
        discordWebhookService.sendErrorToWebhook(ex, url, method);


        BaseErrorCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onFailure(code, ex.getMessage()));
    }

}
