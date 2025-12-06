package com.umc.springboot.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "ReviewRequest", description = "리뷰 조회 필터 요청 DTO")
public class ReviewRequest {

  @Schema(description = "가게 ID. 미지정 시 전체 조회", example = "12")
  private Long storeId;
}
