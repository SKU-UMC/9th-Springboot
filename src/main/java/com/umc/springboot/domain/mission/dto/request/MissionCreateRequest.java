package com.umc.springboot.domain.mission.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "MissionCreateRequest", description = "가게 미션 생성 요청 DTO")
public class MissionCreateRequest {

  @NotBlank(message = "미션 제목은 필수입니다.")
  @Schema(description = "미션 제목", example = "아메리카노 1잔 주문 후 인증샷 올리기")
  private String title;

  @NotBlank(message = "미션 설명은 필수입니다.")
  @Schema(description = "미션 설명", example = "매장 내 포토존에서 사진 찍고 업로드 시 포인트 지급")
  private String description;

  @Schema(description = "미션 마감일 (없으면 null 가능)", example = "2025-12-31")
  private LocalDate deadline;

  @NotBlank(message = "포인트는 필수입니다.")
  @Schema(description = "미션 완료 시 지급 포인트", example = "100")
  private String point;

  @NotNull(message = "미션 날짜는 필수입니다.")
  @Schema(description = "미션 진행 날짜", example = "2025-11-20")
  private LocalDate date;
}
