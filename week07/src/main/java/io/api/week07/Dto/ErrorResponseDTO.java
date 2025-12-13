package io.api.week07.Dto;

import io.api.week07.Code.ErrorCode;
import lombok.Data;

@Data
public class ErrorResponseDTO {
    private int status;
    private String error;
    private String code;
    private String message;

    public ErrorResponseDTO(ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.error = errorCode.getStatus().name();
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
    }
}
