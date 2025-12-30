package com.example.umc9th1.global.validator;

import com.example.umc9th1.global.annotation.ValidPage;
import com.example.umc9th1.global.apipayload.code.GeneralErrorCode;
import com.example.umc9th1.global.apipayload.exception.GeneralException;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class ValidPageArgumentValidator implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ValidPage.class)
                && parameter.getParameterType().equals(Pageable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        String pageStr = webRequest.getParameter("page");
        int page = (pageStr == null) ? 1 : Integer.parseInt(pageStr);

        if (page < 1) {
            throw new GeneralException(GeneralErrorCode.INVALID_PAGE);
        }

        // 한 페이지 10개 고정
        return PageRequest.of(page - 1, 10);
    }
}
