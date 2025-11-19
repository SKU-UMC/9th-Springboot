package com.umc.umc9th.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.umc.umc9th.global.error.code.ErrorCode;
import com.umc.umc9th.global.error.code.GlobalErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ApiResponse<T> {

    private String code; // errorCode (meta)
    private String message; // responseMessage (meta)
    private T data; // api - data

    @Builder
    private ApiResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /// Success

    // 리턴값 없는 응답 성공
    public static ApiResponse<EmptyData> success(String message) {
        return ApiResponse.<EmptyData>builder()
                .code(GlobalErrorCode.SUCCESS.getCode())
                .message(message)
                .build();
    }

    // DTO 타입 지정 응답 성공
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(GlobalErrorCode.SUCCESS.getCode())
                .data(data)
                .build();
    }

    // DTO 타입, 메세지 지정 응답 성공
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .code(GlobalErrorCode.SUCCESS.getCode())
                .message(message)
                .data(data)
                .build();
    }


    /// Fail

    // DTO 타입 지정 응답 실패
    public static <T> ApiResponse<T> fail(ErrorCode error) {
        return ApiResponse.<T>builder()
                .code(error.getCode())
                .message(error.getMessage())
                .build();
    }

    // DTO 타입, 메세지 지정 응답 성공
    public static <T> ApiResponse<T> fail(ErrorCode error, String message) {
        return ApiResponse.<T>builder()
                .code(error.getCode())
                .message(message)
                .build();
    }

}