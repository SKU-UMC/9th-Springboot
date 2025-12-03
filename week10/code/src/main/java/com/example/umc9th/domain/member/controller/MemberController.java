package com.example.umc9th.domain.member.controller;

import com.example.umc9th.domain.member.converter.MemberConverter;
import com.example.umc9th.domain.member.dto.req.MemberReqDTO;
import com.example.umc9th.domain.member.dto.res.MemberResDTO;
import com.example.umc9th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc9th.domain.member.service.MemberService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

    @DeleteMapping("/{memberId}")
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

    // 로그인
    @PostMapping("/login")
    public ApiResponse<MemberResDTO.LoginDTO> login(
            @RequestBody @Valid MemberReqDTO.LoginDTO dto,
            HttpServletResponse response
    ){
        MemberResDTO.LoginTokenDTO result =  memberService.login(dto);

        // AccessToken을 Header에 설정
        response.setHeader("Authorization", "Bearer " + result.accessToken());

        // RefreshToken을 HttpOnly 쿠키로 설정
        ResponseCookie cookie = ResponseCookie.from("refreshToken", result.refreshToken())
                .httpOnly(true)
                .secure(false) // HTTPS만 허용할 경우 true
                .path("/")
                .maxAge(Duration.ofDays(7))
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ApiResponse.onSuccess(MemberSuccessCode.FOUND, MemberResDTO.LoginDTO.of(result.member()));
    }

    @PostMapping("/refresh")
    public ApiResponse<Void> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // Cookie에서 refreshToken 조회
        String refreshToken = memberService.getCookieValue(request, "refreshToken");

        // accessToken 재발급
        MemberResDTO.ReissueDTO token = memberService.reissueToken(refreshToken);

        // AccessToken을 Header에 설정
        response.setHeader("Authorization", "Bearer " + token.accessToken());
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = memberService.getCookieValue(request, "refreshToken");
        memberService.logout(refreshToken);

        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }


}
