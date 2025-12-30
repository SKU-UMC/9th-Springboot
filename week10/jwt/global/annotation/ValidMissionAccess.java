package com.example.umc9th1.global.annotation;

import com.example.umc9th1.global.validator.MissionAccessValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MissionAccessValidator.class)
@Target({ ElementType.PARAMETER })   // PathVariable 대상으로 사용
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMissionAccess {

    String message() default "해당 미션에 접근할 수 없습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
