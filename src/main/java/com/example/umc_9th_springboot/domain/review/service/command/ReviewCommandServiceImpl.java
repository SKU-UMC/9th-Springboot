package com.example.umc_9th_springboot.domain.review.service.command;

import com.example.umc_9th_springboot.domain.review.converter.ReviewConverter;
import com.example.umc_9th_springboot.domain.review.dto.req.ReviewReqDTO;
import com.example.umc_9th_springboot.domain.review.dto.res.ReviewResDTO;
import com.example.umc_9th_springboot.domain.review.entity.Review;
import com.example.umc_9th_springboot.domain.review.repository.ReviewRepository;
import com.example.umc_9th_springboot.domain.shop.entity.Shop;
import com.example.umc_9th_springboot.domain.shop.repository.ShopRepository;
import com.example.umc_9th_springboot.domain.user.entity.User;
import com.example.umc_9th_springboot.domain.user.repository.UserRepository;
import com.example.umc_9th_springboot.global.apiPayload.code.GeneralErrorCode;
import com.example.umc_9th_springboot.global.apiPayload.exception.GeneralException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    // 리뷰 작성
    @Override
    @Transactional
    public ReviewResDTO.CreateDTO createReview(ReviewReqDTO.CreateDTO dto) {

        Long tempUserId = 1L;

        User user = userRepository.findById(tempUserId)
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.NOT_FOUND));

        Shop shop = shopRepository.findById(dto.shopId())
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.NOT_FOUND));

        Review review = ReviewConverter.toReview(dto, user, shop);
        Review saved = reviewRepository.save(review);

        return ReviewConverter.toCreateDTO(saved);
    }
}
