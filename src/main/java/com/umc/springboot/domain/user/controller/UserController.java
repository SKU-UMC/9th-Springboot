package com.umc.springboot.domain.user.controller;

import com.umc.springboot.domain.user.dto.request.UserRequest;
import com.umc.springboot.domain.user.dto.response.UserResponse;
import com.umc.springboot.domain.user.service.UserService;
import com.umc.springboot.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  /**
   * 회원 생성 API
   */
  @Operation(
      summary = "회원가입",
      description = "이름, 생년월일, 전화번호, 이메일을 입력하여 새로운 회원을 생성합니다."
  )
  @PostMapping
  public ResponseEntity<BaseResponse<UserResponse>> create(
      @Valid @RequestBody UserRequest req
  ) {
    UserResponse response = userService.createUser(req);
    return ResponseEntity.ok(BaseResponse.success(response));
  }

  /**
   * 회원 단건 조회 API
   */
  @Operation(
      summary = "회원 단건 조회",
      description = "회원 ID를 통해 회원 정보를 조회합니다."
  )
  @GetMapping("/{id}")
  public ResponseEntity<BaseResponse<UserResponse>> get(@PathVariable Long id) {
    UserResponse response = userService.getUser(id);
    return ResponseEntity.ok(BaseResponse.success(response));
  }
}
