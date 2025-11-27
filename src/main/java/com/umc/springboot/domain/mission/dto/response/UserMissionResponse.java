package com.umc.springboot.domain.mission.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "UserMissionResponse", description = "사용자 미션(도전 중) 응답 DTO")
public class UserMissionResponse {

  @Schema(description = "유저-미션 ID", example = "5")
  private Long userMissionId;

  @Schema(description = "미션 ID", example = "1")
  private Long missionId;

  @Schema(description = "가게 ID", example = "10")
  private Long storeId;

  @Schema(description = "미션 제목")
  private String title;

  @Schema(description = "완료 여부", example = "false")
  private Boolean isCompleted;
}
