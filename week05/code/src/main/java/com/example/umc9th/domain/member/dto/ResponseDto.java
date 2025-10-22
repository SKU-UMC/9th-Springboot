package com.example.umc9th.domain.member.dto;

import lombok.Getter;

@Getter
public class ResponseDto<T> {
    private Integer statusCode;
    private String message;
    private T data;

    public ResponseDto(Integer statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
    public ResponseDto(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
