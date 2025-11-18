package com.example.umc9th.domain.review.converter;

import com.example.umc9th.domain.review.dto.res.ReviewResDTO;
import com.example.umc9th.domain.review.entity.Review;

public class ReviewConverter {

    public static ReviewResDTO.Search toSearchDTO(Review review){
        return ReviewResDTO.Search.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .star(review.getStar())
                .memberId(review.getMember().getId())
                .replyId(review.getReply().getId())
                .storeId(review.getStore().getId())
                .build();
    }
}
