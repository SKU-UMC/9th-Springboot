package com.example.umc_9th_springboot.domain.review.converter;

import com.example.umc_9th_springboot.domain.review.dto.req.ReviewReqDTO;
import com.example.umc_9th_springboot.domain.review.dto.res.ReviewResDTO;
import com.example.umc_9th_springboot.domain.review.entity.Review;
import com.example.umc_9th_springboot.domain.shop.entity.Shop;
import com.example.umc_9th_springboot.domain.user.entity.User;
import org.springframework.data.domain.Page;


public class ReviewConverter {

    // 리뷰 생성 요청 DTO -> 엔티티
    public static Review toReview(ReviewReqDTO.CreateDTO dto, User user, Shop shop) {
        return Review.builder()
                .rating(dto.rating().doubleValue())
                .content(dto.content())
                .user(user)
                .shop(shop)
                .build();
    }

    //엔티티 -> 리뷰 생성 응답 DTO
    public static ReviewResDTO.CreateDTO toCreateDTO(Review review) {
        return ReviewResDTO.CreateDTO.builder()
                .reviewId(review.getId())
                .userId(review.getUser().getId())
                .shopId(review.getShop().getId())
                .rating((int) review.getRating())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .build();
    }

    // 해당 사용자 리뷰 페이지 → DTO 변환
    public static ReviewResDTO.ReviewPreViewListDTO toReviewPreviewListDTO(Page<Review> result) {
        return ReviewResDTO.ReviewPreViewListDTO.builder()
                .reviewList(
                        result.getContent().stream()
                                .map(ReviewConverter::toReviewPreviewDTO)
                                .toList()
                )
                .listSize(result.getSize())
                .totalPage(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .isFirst(result.isFirst())
                .isLast(result.isLast())
                .build();
    }

    // 엔티티 →  해당 사용자 리뷰 DTO
    public static ReviewResDTO.ReviewPreViewDTO toReviewPreviewDTO(Review review) {
        return ReviewResDTO.ReviewPreViewDTO.builder()
                .reviewId(review.getId())
                .ownerNickname(review.getUser().getName())   // User 기준으로 변경
                .rating(review.getRating())
                .content(review.getContent())
                .createdAt(review.getCreatedAt().toLocalDate())
                .build();
    }
}
