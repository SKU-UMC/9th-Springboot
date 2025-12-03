package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.exception.MemberException;
import com.example.umc9th.domain.member.exception.code.MemberErrorCode;
import com.example.umc9th.domain.member.repository.MemberRepository;
import com.example.umc9th.domain.review.converter.ReviewConverter;
import com.example.umc9th.domain.review.dto.req.ReviewReqDTO;
import com.example.umc9th.domain.review.dto.res.ReviewResDTO;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.exception.StoreException;
import com.example.umc9th.domain.store.exception.code.StoreErrorCode;
import com.example.umc9th.domain.store.repository.StoreRepository;
import com.example.umc9th.global.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public ReviewResDTO.AddDTO addReview(ReviewReqDTO.AddDTO dto) {
        Member member = memberRepository.findById(dto.memberId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Store store = storeRepository.findById(dto.storeId())
                .orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));

        Review review = ReviewConverter.toAddEntity(dto, member, store);

        Review savedReview = reviewRepository.save(review);
        return ReviewConverter.toAddDTO(savedReview);
    }

    @Transactional
    public PageDTO<ReviewResDTO.ReviewPreViewDTO> findReview(String storeName, Pageable pageable){
        // - 가게를 가져온다 (가게 존재 여부 검증)
        Store store = storeRepository.findByName(storeName)
                //    - 없으면 예외 터뜨린다
                .orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));

        //- 가게에 맞는 리뷰를 가져온다 (Offset 페이징)
        Pageable unsortedPageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());
        Page<Review> result = reviewRepository.findAllByStore(store, unsortedPageable);

        //- 결과를 응답 DTO로 변환한다 (컨버터 이용)
        return PageDTO.of(result, ReviewConverter::toReviewPreviewDTO);
    }

    @Transactional(readOnly = true)
    public PageDTO<ReviewResDTO.ReviewPreViewDTO> getMyReviews(Long memberId, Pageable pageable){
        //sort값 무시
        Pageable unsortedPageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());
        Page<Review> reviews = reviewRepository.findByMemberId(memberId, unsortedPageable);

        return PageDTO.of(reviews, ReviewConverter::toReviewPreviewDTO);
    }
}
