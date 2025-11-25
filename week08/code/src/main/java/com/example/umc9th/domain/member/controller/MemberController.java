package com.example.umc9th.domain.member.controller;

import com.example.umc9th.domain.member.converter.MemberConverter;
import com.example.umc9th.domain.member.dto.req.MemberReqDTO;
import com.example.umc9th.domain.member.dto.res.MemberResDTO;
import com.example.umc9th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc9th.domain.member.service.MemberService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {
    private final MemberService memberService;

    @DeleteMapping("/member/{memberId}")
    public ApiResponse<MemberResDTO.Delete> deleteMember(@PathVariable Long memberId) {
        memberService.withdraw(memberId);

        MemberResDTO.Delete resultDTO = MemberConverter.toDeleteDTO(memberId);
        GeneralSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, resultDTO);
    }

    // 회원가입
    @PostMapping("/sign-up")
    public ApiResponse<MemberResDTO.JoinDTO> signUp(@RequestBody @Valid MemberReqDTO.JoinDTO dto){
        return ApiResponse.onSuccess(MemberSuccessCode.FOUND, memberService.signup(dto));
    }

}
