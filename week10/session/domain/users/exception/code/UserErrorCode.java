package com.example.umc9th1.domain.users.exception.code;

import com.example.umc9th1.global.apipayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"USER404_1","유저를 찾을 수 없습니다."),
    USER_PASSWORD_ERROR(HttpStatus.UNAUTHORIZED,"user401_1","비밀번호 오류");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
