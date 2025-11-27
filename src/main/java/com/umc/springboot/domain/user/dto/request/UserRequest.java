package com.umc.springboot.domain.user.dto.request;

import com.umc.springboot.domain.user.entity.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

  @NotBlank(message = "이름은 필수입니다.")
  private String name;

  private Gender gender;

  private LocalDate birth;

  private String address;

  @Email(message = "올바른 이메일 형식이 아닙니다.")
  @NotBlank(message = "이메일은 필수입니다.")
  private String email;

  @NotBlank(message = "전화번호는 필수입니다.")
  private String phone;

  // 로그인 요청 DTO (이메일 + 전화번호)
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class LoginRequest {

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @NotBlank(message = "전화번호는 필수입니다.")
    private String phone;
  }
}
