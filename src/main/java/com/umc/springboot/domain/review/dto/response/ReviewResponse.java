package com.umc.springboot.domain.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ReviewResponse", description = "리뷰 요약 응답")
public class ReviewResponse {

  @Schema(description = "리뷰 ID", example = "101")
  private Long reviewId;

  @Schema(description = "가게 ID", example = "12")
  private Long storeId;

  @Schema(description = "가게명", example = "반이학생마라탕마라반")
  private String storeName;

  @Schema(description = "리뷰 제목")
  private String title;

  @Schema(description = "리뷰 본문(요약)")
  private String description;

  @Schema(description = "별점(소수1자리)", example = "4.5")
  private BigDecimal rating;

  @Schema(description = "이미지 URL 목록")
  private List<String> imageUrls;

  @Schema(description = "작성 시각", example = "2025-08-21T10:15:30")
  private LocalDateTime createdAt;
}
