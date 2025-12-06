package com.umc.springboot.domain.mission.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserMissionUpdateRequest {

  private Boolean isCompleted;
}
