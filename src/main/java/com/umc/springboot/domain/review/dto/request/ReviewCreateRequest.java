package com.umc.springboot.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "ReviewCreateRequest", description = "리뷰 생성 요청 DTO")
public class ReviewCreateRequest {

  @NotBlank(message = "리뷰 제목은 필수입니다.")
  @Size(max = 50, message = "리뷰 제목은 50자 이하여야 합니다.")
  @Schema(description = "리뷰 제목", example = "맛도 분위기도 최고였어요!")
  private String title;

  @NotBlank(message = "리뷰 내용은 필수입니다.")
  @Size(max = 100, message = "리뷰 내용은 100자 이하여야 합니다.")
  @Schema(description = "리뷰 내용", example = "재방문 의사 100%! 친구들이랑 또 올 거예요.")
  private String description;

  @NotNull(message = "별점은 필수입니다.")
  @DecimalMin(value = "0.5", message = "별점은 최소 0.5 이상이어야 합니다.")
  @DecimalMax(value = "5.0", message = "별점은 최대 5.0 이하여야 합니다.")
  @Schema(description = "별점(0.5 ~ 5.0)", example = "4.5")
  private BigDecimal rating;

  @Schema(description = "리뷰 이미지 URL 목록",
      example = "[\"https://image1.png\", \"https://image2.png\"]")
  private List<String> imageUrls;
}
