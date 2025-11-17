package com.umc.springboot.domain.review.exception;

import com.umc.springboot.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

  EXAMPLE_ERROR_CODE("REVIEW_0000", "예시 에러코드로 커스터마이징이 필요합니다.", HttpStatus.BAD_REQUEST),

  REVIEW_NOT_FOUND("REVIEW_0001", "리뷰를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  REVIEW_ACCESS_DENIED("REVIEW_0005", "해당 리뷰에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN),

  DUPLICATE_REVIEW("REVIEW_0006", "이미 리뷰를 작성한 이력이 있습니다.", HttpStatus.CONFLICT),
  INVALID_REVIEW_RATING("REVIEW_0007", "리뷰 평점이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
  INVALID_REVIEW_CONTENT("REVIEW_0008", "리뷰 내용이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),

  REVIEW_IMAGE_UPLOAD_FAILED("REVIEW_0009", "리뷰 이미지 업로드에 실패했습니다.",
      HttpStatus.INTERNAL_SERVER_ERROR),
  REVIEW_IMAGE_DELETE_FAILED("REVIEW_0010", "리뷰 이미지 삭제에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
