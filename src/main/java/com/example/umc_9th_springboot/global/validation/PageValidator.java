package com.example.umc_9th_springboot.global.validation;

import com.example.umc_9th_springboot.global.apiPayload.code.PagingErrorCode;
import com.example.umc_9th_springboot.global.apiPayload.exception.GeneralException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


 //page 값 범위 검증
public class PageValidator implements ConstraintValidator<ValidPage, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // null 여부는 @RequestParam(required = true)로 처리
        if (value == null) {
            return true;
        }

        if (value < 1) {
            throw new GeneralException(PagingErrorCode.INVALID_PAGE);
        }

        return true;
    }
}
