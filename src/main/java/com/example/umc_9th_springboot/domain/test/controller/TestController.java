package com.example.umc_9th_springboot.domain.test.controller;

import com.example.umc_9th_springboot.domain.test.converter.TestConverter;
import com.example.umc_9th_springboot.domain.test.dto.req.TestReqDTO;
import com.example.umc_9th_springboot.domain.test.dto.res.TestResDTO;
import com.example.umc_9th_springboot.domain.test.service.query.TestQueryService;
import com.example.umc_9th_springboot.global.apiPayload.ApiResponse;
import com.example.umc_9th_springboot.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
/*
에러테스트
http://localhost:8080/temp/exception?flag=1
성공테스트
http://localhost:8080/temp/exception?flag=2
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/temp")
public class TestController {

    private final TestQueryService testQueryService;

    // 1. 정상 응답
    @GetMapping("/test")
    public ApiResponse<TestResDTO.Testing> test() {
        GeneralSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(
                code,
                TestConverter.toTestingDTO("This is Test!")
        );
    }

    // 2. 예외 상황
    @GetMapping("/exception")
    public ApiResponse<TestResDTO.Exception> exception(
            @RequestParam Long flag
    ) {
        testQueryService.checkFlag(flag);

        GeneralSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(
                code,
                TestConverter.toExceptionDTO("This is Test!")
        );
    }

    // 3. 요청 DTO
    @PostMapping("/req-test")
    public ApiResponse<TestResDTO.Testing> reqTest(
            @RequestBody TestReqDTO.TestFlagReq request
    ) {
        testQueryService.checkFlag(request.getFlag());

        GeneralSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(
                code,
                TestConverter.toTestingDTO("요청 성공! flag = " + request.getFlag())
        );
    }
}
