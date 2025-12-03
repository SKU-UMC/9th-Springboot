package com.example.umc_9th_springboot.domain.review.service;

import com.example.umc_9th_springboot.domain.review.converter.ReviewConverter;
import com.example.umc_9th_springboot.domain.review.dto.ReviewResponse;
import com.example.umc_9th_springboot.domain.review.dto.req.ReviewReqDTO;
import com.example.umc_9th_springboot.domain.review.dto.res.ReviewResDTO;
import com.example.umc_9th_springboot.domain.review.entity.Review;
import com.example.umc_9th_springboot.domain.review.enums.StarScore;
import com.example.umc_9th_springboot.domain.review.repository.ReviewRepository;
import com.example.umc_9th_springboot.domain.shop.entity.Shop;
import com.example.umc_9th_springboot.domain.shop.repository.ShopRepository;
import com.example.umc_9th_springboot.domain.user.entity.User;
import com.example.umc_9th_springboot.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    // 가게명 + 별점대 API
    public Page<ReviewResponse> getReviews(String shopName, Integer score, Pageable pageable) {
        return reviewRepository.search(shopName, StarScore.from(score), pageable);
    }

    // 리뷰 작성 API
    @Transactional
    public ReviewResDTO.CreateDTO createReview(ReviewReqDTO.CreateDTO dto) {

        Long tempUserId = 1L;

        // 유저 조회
        User user = userRepository.findById(tempUserId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 가게 조회
        Shop shop = shopRepository.findById(dto.shopId())
                .orElseThrow(() -> new IllegalArgumentException("가게를 찾을 수 없습니다."));

        // 리뷰 엔티티 생성
        Review review = ReviewConverter.toReview(dto, user, shop);

        // 저장
        Review savedReview = reviewRepository.save(review);

        // 응답 DTO 변환
        return ReviewConverter.toCreateDTO(savedReview);
    }

    // 내가 작성한 리뷰 목록 조회 API
    @Transactional
    public ReviewResDTO.ReviewPreViewListDTO getMyReviews(Long userId, Integer page) {

        // 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // page는 프론트에서 1부터, JPA는 0부터 시작하니까 -1 해줌
        int pageIndex = page - 1;

        // 한 페이지에 10개씩 조회
        org.springframework.data.domain.PageRequest pageRequest =
                org.springframework.data.domain.PageRequest.of(pageIndex, 10);

        // 해당 유저가 작성한 리뷰들만 페이징 조회
        Page<Review> result = reviewRepository.findAllByUser(user, pageRequest);

        // 엔티티 Page → 응답 DTO로 변환
        return ReviewConverter.toReviewPreviewListDTO(result);
    }


}
