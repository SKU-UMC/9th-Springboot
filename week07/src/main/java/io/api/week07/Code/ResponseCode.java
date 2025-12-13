package io.api.week07.Code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS_GET_TOKEN(HttpStatus.OK, "토큰을 성공적으로 불러왔습니다.")

    ;


    private final HttpStatus status;
    private final String message;
}
