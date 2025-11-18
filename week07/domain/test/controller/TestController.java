package com.example.umc9th1.domain.test.controller;

import com.example.umc9th1.domain.test.converter.TestConverter;
import com.example.umc9th1.domain.test.dto.TestResDto;
import com.example.umc9th1.domain.test.exception.TestException;
import com.example.umc9th1.domain.test.service.query.TestQueryService;
import com.example.umc9th1.global.apipayload.ApiResponse;
import com.example.umc9th1.global.apipayload.code.GeneralErrorCode;
import com.example.umc9th1.global.apipayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/temp")
@RequiredArgsConstructor
public class TestController {
    private final TestQueryService testQueryService;


    @PostMapping("/test")
    public ResponseEntity<ApiResponse<TestResDto.Testing>> test(){

        GeneralSuccessCode code = GeneralSuccessCode.COMMENT_CREATED;

       return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onSuccess(code,TestConverter.toTestResDto("this is test")));
    }

    @GetMapping("/exception")
    public ResponseEntity<ApiResponse<TestResDto.Exception>>  testException(
            @RequestParam Long flag
    ){
        testQueryService.checkFlag(flag);
        GeneralSuccessCode code = GeneralSuccessCode.SUCCESS;
        return ResponseEntity.status(code.getStatus()).body(ApiResponse.onSuccess(code,TestConverter
                .toExceptionDto("this is test")));
    }

}
