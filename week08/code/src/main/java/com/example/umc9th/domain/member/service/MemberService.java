package com.example.umc9th.domain.member.service;

import com.example.umc9th.domain.member.converter.MemberConverter;
import com.example.umc9th.domain.member.dto.req.MemberReqDTO;
import com.example.umc9th.domain.member.dto.res.MemberResDTO;
import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.entity.mapping.MemberFood;
import com.example.umc9th.domain.member.exception.FoodException;
import com.example.umc9th.domain.member.exception.MemberException;
import com.example.umc9th.domain.member.exception.code.FoodErrorCode;
import com.example.umc9th.domain.member.exception.code.MemberErrorCode;
import com.example.umc9th.domain.member.repository.*;
import com.example.umc9th.domain.review.repository.ReplyRepository;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberTermRepository memberTermRepository;
    private final MemberRepository memberRepository;
    private final MemberFoodRepository memberFoodRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final ReviewRepository reviewRepository;
    private final ReplyRepository replyRepository;
    private final FoodRepository foodRepository;

    @Transactional
    public void withdraw(Long memberId) {
        if(!memberRepository.existsById(memberId)) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
        memberTermRepository.deleteAllByMemberIdInBatch(memberId);
        memberFoodRepository.deleteAllByMemberIdInBatch(memberId);
        memberMissionRepository.deleteAllByMemberIdInBatch(memberId);

        List<Long> reviewIds = reviewRepository.findAllIdsByMemberId(memberId);
        if(reviewIds == null || reviewIds.isEmpty()) {
            replyRepository.deleteAllByReviewIdsInBatch(reviewIds);
            reviewRepository.deleteAllByIdsInBatch(reviewIds);
        }
        memberRepository.deleteById(memberId);

    }

    @Transactional
    public MemberResDTO.JoinDTO signup(MemberReqDTO.JoinDTO dto){
        // 사용자 생성
        Member member = MemberConverter.toMember(dto);
        // DB 적용
        memberRepository.save(member);

        // 선호 음식 존재 여부 확인
        if (dto.preferCategory().size() > 1){
            List<MemberFood> memberFood = dto.preferCategory().stream()
                    .map(id -> MemberFood.builder()
                            .member(member)
                            .food(foodRepository.findById(id)
                                    .orElseThrow(() -> new FoodException(FoodErrorCode.NOT_FOUND)))
                            .build()
                    )
                    .collect(Collectors.toList());

            memberFoodRepository.saveAll(memberFood);
        }


        // 응답 DTO 생성
        return MemberConverter.toJoinDTO(member);
    }

}
