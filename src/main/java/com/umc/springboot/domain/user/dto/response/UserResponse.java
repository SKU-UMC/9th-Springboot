package com.umc.springboot.domain.user.dto.response;

import com.umc.springboot.domain.user.entity.Gender;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

  private Long id;
  private String name;
  private Gender gender;
  private LocalDate birth;
  private String email;
  private String phone;
  private String address;
}
