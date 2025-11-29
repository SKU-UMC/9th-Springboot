package com.example.umc_9th_springboot.domain.review.service.command;

import com.example.umc_9th_springboot.domain.review.dto.req.ReviewReqDTO;
import com.example.umc_9th_springboot.domain.review.dto.res.ReviewResDTO;

public interface ReviewCommandService {

    // 리뷰 작성
    ReviewResDTO.CreateDTO createReview(ReviewReqDTO.CreateDTO dto);
}
