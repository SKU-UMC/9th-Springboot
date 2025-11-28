package com.umc.umc9th.global.error.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.umc.umc9th.global.error.code.GlobalErrorCode;
import com.umc.umc9th.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(CustomException e) {
        log.error("CustomException : {}", e.getMessage(), e);
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ApiResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        log.error("AccessDeniedException : {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("접근 권한이 없습니다.");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        log.error("Exception : {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("잘못된 요청입니다.");
    }


    ///

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            IllegalArgumentException.class,
            HttpMessageNotReadableException.class,
            InvalidFormatException.class,
            ServletRequestBindingException.class
    })
    public ResponseEntity<ApiResponse<?>> handleBadRequestException(Exception e) {
        log.error("BadRequestException : {}", e.getMessage(), e);
        return ResponseEntity
                .status(GlobalErrorCode.BAD_REQUEST.getStatus())
                .body(ApiResponse.fail(GlobalErrorCode.BAD_REQUEST));
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFoundException(Exception e) {
        log.error("NotFoundException : {}", e.getMessage(), e);
        return ResponseEntity
                .status(GlobalErrorCode.NOT_FOUND.getStatus())
                .body(ApiResponse.fail(GlobalErrorCode.NOT_FOUND));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.error("Exception : {}", e.getMessage(), e);
        return ResponseEntity
                .status(GlobalErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiResponse.fail(GlobalErrorCode.INTERNAL_SERVER_ERROR));
    }

}
