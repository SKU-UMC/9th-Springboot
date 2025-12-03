package com.example.umc9th.global.validator;

import com.example.umc9th.domain.member.exception.code.FoodErrorCode;
import com.example.umc9th.global.annotation.ExistFoods;
import com.example.umc9th.global.annotation.ValidPage;
import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
@RequiredArgsConstructor
public class PageValidator implements ConstraintValidator<ValidPage, Pageable> {
    @Override
    public boolean isValid(Pageable value, ConstraintValidatorContext context) {
        boolean isValid = (value.getPageNumber() > 0);
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(GeneralErrorCode.PAGE_INVALID.getMessage()).addConstraintViolation();
        }
        return isValid;

    }
}
