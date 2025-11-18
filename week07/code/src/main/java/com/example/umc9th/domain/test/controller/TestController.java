package com.example.umc9th.domain.test.controller;

import com.example.umc9th.domain.test.converter.TestConverter;
import com.example.umc9th.domain.test.dto.res.TestResDTO;
import com.example.umc9th.domain.test.service.query.TestQueryService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/temp")
public class TestController {

    private final TestQueryService testQueryService;

    @GetMapping("/test")
    public ApiResponse<TestResDTO.Testing> test() throws Exception{
        //응답 코드 정의
        GeneralSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, TestConverter.toTestingDTO("This is Test!"));
    }

    @GetMapping("/exception")
    public ApiResponse<TestResDTO.Exception> exception(@RequestParam Long flag){

        testQueryService.checkFlag(flag);

        GeneralSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, TestConverter.toExceptionDTO("This is Test!"));
    }

    @GetMapping("/webhook")
    public ApiResponse<TestResDTO.Testing> webhookTest(){
        try{
            throw new Exception();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
