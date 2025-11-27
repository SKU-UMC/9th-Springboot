package com.umc.springboot.domain.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreRequest {

  @NotBlank(message = "매장명은 필수입니다.")
  private String name;

  @NotBlank(message = "사업자 번호는 필수입니다.")
  private String managerNumber;

  @NotBlank(message = "상세 주소는 필수입니다.")
  private String detailAddress;

  @NotBlank(message = "매장 설명은 필수입니다.")
  private String description;

  @NotBlank(message = "전화번호는 필수입니다.")
  private String phone;

  @NotBlank(message = "오픈 시간은 필수입니다.")
  private String openTime;

  @NotBlank(message = "마감 시간은 필수입니다.")
  private String closeTime;
}
