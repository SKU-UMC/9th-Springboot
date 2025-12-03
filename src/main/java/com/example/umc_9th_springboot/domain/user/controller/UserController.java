package com.example.umc_9th_springboot.domain.user.controller;

import com.example.umc_9th_springboot.domain.user.dto.req.UserReqDTO;
import com.example.umc_9th_springboot.domain.user.dto.res.UserResDTO;
import com.example.umc_9th_springboot.domain.user.service.UserCommandService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserCommandService userCommandService;

    //회원가입 세션 API
    @PostMapping("/sign-up/session")
    public UserResDTO.SignUpDTO signUp(@RequestBody UserReqDTO.SignUpDTO request) {
        return userCommandService.signup(request);
    }

    // 로그인 세션 API
    @PostMapping("/login/session")
    public UserResDTO.LoginSessionDTO loginSession(
            @RequestBody UserReqDTO.LoginSessionDTO request,
            HttpServletRequest httpRequest
    ) {
        UserResDTO.LoginSessionDTO response = userCommandService.login(request);

        // 세션 생성 및 사용자 정보 저장
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute("loginUserId", response.getUserId());
        session.setAttribute("loginUserEmail", response.getEmail());

        return response;
    }
    // 로그아웃 세션 방식
    @PostMapping("/logout/session")
    public void logoutSession(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    // 로그인 JWT 방식 API
    @PostMapping("/login/jwt")
    public UserResDTO.LoginJwtDTO loginJwt(
            @RequestBody UserReqDTO.LoginJwtDTO request
    ) {
        return userCommandService.loginJwt(request);
    }

    // jwt 회원가입 API
    @PostMapping("/sign-up/jwt")
    public UserResDTO.SignUpJwtDTO signUpJwt(@RequestBody UserReqDTO.SignUpDTO request) {
        return userCommandService.signupJwt(request);
    }
}
