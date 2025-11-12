package com.umc.springboot.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "ReviewRequest", description = "리뷰 조회 필터")
public class ReviewRequest {

  @Schema(description = "가게명(정확 일치). 미지정 시 전체", example = "반이학생마라탕마라반")
  private String storeName;

  @Min(1)
  @Max(5)
  @Schema(description = "별점대(1~5). 4 ⇒ [4.0,5.0), 5 ⇒ 5.0만", example = "4")
  private Integer ratingBand;
}
