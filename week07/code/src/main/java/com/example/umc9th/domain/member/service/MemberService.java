package com.example.umc9th.domain.member.service;

import com.example.umc9th.domain.member.exception.MemberException;
import com.example.umc9th.domain.member.exception.code.MemberErrorCode;
import com.example.umc9th.domain.member.repository.MemberFoodRepository;
import com.example.umc9th.domain.member.repository.MemberMissionRepository;
import com.example.umc9th.domain.member.repository.MemberRepository;
import com.example.umc9th.domain.member.repository.MemberTermRepository;
import com.example.umc9th.domain.review.repository.ReplyRepository;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberTermRepository memberTermRepository;
    private final MemberRepository memberRepository;
    private final MemberFoodRepository memberFoodRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final ReviewRepository reviewRepository;
    private final ReplyRepository replyRepository;

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

}
