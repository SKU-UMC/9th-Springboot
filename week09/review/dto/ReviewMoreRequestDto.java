package com.example.umc9th1.domain.review.dto;

import com.example.umc9th1.global.annotation.ValidMissionReview;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@ValidMissionReview
public class ReviewMoreRequestDto {

    @JsonIgnore
    private Long userId;

    @JsonIgnore
    private Long missionId;

    @NotNull(message = "별점은 필수입니다.")
    @DecimalMin(value = "0.5", message = "별점은 0.5 이상이어야 합니다.")
    @DecimalMax(value = "5.0", message = "별점은 5.0 이하여야 합니다.")
    private BigDecimal star;

    @NotBlank(message = "리뷰 내용은 비어 있을 수 없습니다.")
    private String content;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }
}
