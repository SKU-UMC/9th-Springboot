package com.umc.springboot.domain.mission.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "MissionResponse", description = "미션 응답 DTO")
public class MissionResponse {

  @Schema(description = "미션 ID", example = "1")
  private Long missionId;

  @Schema(description = "가게 ID", example = "10")
  private Long storeId;

  @Schema(description = "미션 제목")
  private String title;

  @Schema(description = "미션 설명")
  private String description;

  @Schema(description = "마감일")
  private LocalDate deadline;

  @Schema(description = "포인트")
  private String point;

  @Schema(description = "미션 날짜")
  private LocalDate date;
}
