package com.example.umc9th1.global.annotation;

import com.example.umc9th1.global.validator.MissionReviewValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = MissionReviewValidator.class)
public @interface ValidMissionReview {

    String message() default "리뷰 작성 조건을 만족하지 않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
