package com.example.umc9th1.global.apipayload;

import com.example.umc9th1.global.apipayload.code.BaseErrorCode;
import com.example.umc9th1.global.apipayload.code.BaseSuccessCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    @JsonProperty("code")
    private final String code;

    @JsonProperty("message")
    private final String message;

    @JsonProperty("result")
    private T result;

    //성공한 경우
    public static <T> ApiResponse<T> onSuccess(BaseSuccessCode code, T result) {

        return new ApiResponse<>(true ,code.getCode(), code.getMessage(), result);
    }

    // 실패한 경우
    public static <T> ApiResponse<T> onFailure(BaseErrorCode code, T result) {
        return new ApiResponse<>(false, code.getCode(), code.getMessage(), result);
    }
}
