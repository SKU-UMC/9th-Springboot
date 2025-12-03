package com.example.umc9th.domain.review.converter;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.review.dto.req.ReviewReqDTO;
import com.example.umc9th.domain.review.dto.res.ReviewResDTO;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.store.entity.Store;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

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

    public static Review toAddEntity(ReviewReqDTO.AddDTO dto, Member member, Store store){
        return Review.builder()
                .content(dto.content())
                .star(dto.star())
                .member(member)
                .store(store)
                .build();
    }

    public static ReviewResDTO.AddDTO toAddDTO(Review review){
        return ReviewResDTO.AddDTO.builder()
                .reviewId(review.getId())
                .createAt(review.getCreatedAt())
                .build();
    }

    // result -> DTO
    public static ReviewResDTO.ReviewPreViewListDTO toReviewPreviewListDTO(Page<Review> result){
        return ReviewResDTO.ReviewPreViewListDTO.builder()
                .reviewList(result.getContent().stream()
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

    public static ReviewResDTO.ReviewPreViewDTO toReviewPreviewDTO(Review review){
        return ReviewResDTO.ReviewPreViewDTO.builder()
                .ownerNickname(review.getMember().getName())
                .score(review.getStar())
                .body(review.getContent())
                .createdAt(LocalDate.from(review.getCreatedAt()))
                .build();
    }
}
