package com.umc.umc9th.global.error.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getStatus();
    String getCode();
    String getMessage();
}
