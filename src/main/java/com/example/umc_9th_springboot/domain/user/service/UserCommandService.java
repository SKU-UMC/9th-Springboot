package com.example.umc_9th_springboot.domain.user.service;

import com.example.umc_9th_springboot.domain.user.dto.req.UserReqDTO;
import com.example.umc_9th_springboot.domain.user.dto.res.UserResDTO;

public interface UserCommandService {

    // Session 회원가입
    UserResDTO.SignUpDTO signup(UserReqDTO.SignUpDTO dto);
    // Session 로그인
    UserResDTO.LoginSessionDTO login(UserReqDTO.LoginSessionDTO dto);

    // jwt 로그인
    UserResDTO.LoginJwtDTO loginJwt(UserReqDTO.LoginJwtDTO dto);

    // jwt 회원가입
    UserResDTO.SignUpJwtDTO signupJwt(UserReqDTO.SignUpDTO dto);
}
