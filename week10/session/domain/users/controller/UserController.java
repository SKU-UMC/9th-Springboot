package com.example.umc9th1.domain.users.controller;

import com.example.umc9th1.domain.users.dto.*;
import com.example.umc9th1.domain.users.service.UserService;
import com.example.umc9th1.global.apipayload.ApiResponse;
import com.example.umc9th1.global.apipayload.code.GeneralSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto request) {
        return userService.signUp(request);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto request, HttpSession session) {

        userService.login(request, session);
        GeneralSuccessCode code = GeneralSuccessCode.SUCCESS;
        return ResponseEntity.status(code.getStatus()).body(ApiResponse.onSuccess(code,null));
    }
  }
