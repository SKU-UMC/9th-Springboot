package com.umc.umc9th.domain.review.dto.request;

public record MyReviewsSearchRequest(
        Long userId
) {
    // 사용자의 작성 리뷰 조회를 위한 리퀘스트dto 선언을 통하여 리퀘스트바디에 userId 담기 -> 추후에 jwt 학습 후 토큰을 통한 인증 시
    // 리팩토링해서 필드 하나짜리 dto는 낭비라 생각하기 때문에 / 해당 dto는 없애주기 +++ controller / service 단 또한 수정 필요
}
