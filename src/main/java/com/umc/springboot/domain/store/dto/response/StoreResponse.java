package com.umc.springboot.domain.store.dto.response;

import com.umc.springboot.domain.store.entity.District;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreResponse {

  private Long id;
  private String name;
  private String managerNumber;
  private District district;
  private String detailAddress;
  private String description;
  private String phone;
  private String openTime;
  private String closeTime;
}
