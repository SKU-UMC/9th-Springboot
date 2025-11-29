package com.example.umc_9th_springboot.domain.review.converter;

import com.example.umc_9th_springboot.domain.review.dto.req.ReviewReqDTO;
import com.example.umc_9th_springboot.domain.review.dto.res.ReviewResDTO;
import com.example.umc_9th_springboot.domain.review.entity.Review;
import com.example.umc_9th_springboot.domain.shop.entity.Shop;
import com.example.umc_9th_springboot.domain.user.entity.User;

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
}
