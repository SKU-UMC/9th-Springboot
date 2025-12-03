package com.example.umc9th.domain.member.service;

import com.example.umc9th.domain.member.converter.MemberConverter;
import com.example.umc9th.domain.member.dto.req.MemberReqDTO;
import com.example.umc9th.domain.member.dto.res.MemberResDTO;
import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.entity.RefreshToken;
import com.example.umc9th.domain.member.entity.mapping.MemberFood;
import com.example.umc9th.domain.member.exception.FoodException;
import com.example.umc9th.domain.member.exception.MemberException;
import com.example.umc9th.domain.member.exception.code.FoodErrorCode;
import com.example.umc9th.domain.member.exception.code.MemberErrorCode;
import com.example.umc9th.domain.member.repository.*;
import com.example.umc9th.domain.review.repository.ReplyRepository;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.global.auth.details.CustomUserDetails;
import com.example.umc9th.global.auth.dto.KakaoResDTO;
import com.example.umc9th.global.auth.enums.Role;
import com.example.umc9th.global.auth.token.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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
        String salt = passwordEncoder.encode(dto.password());
        // 사용자 생성
        Member member = MemberConverter.toMember(dto, salt, Role.ROLE_USER);
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

    @Transactional
    public MemberResDTO.LoginTokenDTO login(
            MemberReqDTO.@Valid LoginDTO dto
    ) {

        // Member 조회
        Member member = memberRepository.findByEmail(dto.email())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 비밀번호 검증
        if (!passwordEncoder.matches(dto.password(), member.getPassword())){
            throw new MemberException(MemberErrorCode.INVALID);
        }

        // JWT 토큰 발급용 UserDetails
        CustomUserDetails userDetails = new CustomUserDetails(member);

        // 엑세스 토큰 발급
        String accessToken = jwtUtil.createAccessToken(userDetails);
        String refreshToken = jwtUtil.createRefreshToken(userDetails);

        saveOrUpdateRefreshToken(member, refreshToken);

        // DTO 조립
        return MemberResDTO.LoginTokenDTO.of(member, accessToken, refreshToken);
    }

    @Transactional
    public MemberResDTO.LoginTokenDTO loginOrSignup(KakaoResDTO.UserInfoDTO kakaoUserInfo) {

        Long kakaoId = kakaoUserInfo.id();
        String email = kakaoUserInfo.kakaoAccount().email();

        // 1. DB에서 카카오 ID로 회원 조회, 없으면 새로 생성
        Member member = memberRepository.findBySocialUid(kakaoId)
                .orElseGet(() -> {
                    // 신규 회원가입
                    Member newMember = Member.builder()
                            .socialUid(kakaoId)
                            .email(email)
                            .role(Role.ROLE_USER)
                            .build();
                    return memberRepository.save(newMember);
                });


        // 2. 우리 서비스의 JWT 토큰 발급
        CustomUserDetails userDetails = new CustomUserDetails(member);
        String accessToken = jwtUtil.createAccessToken(userDetails);
        String refreshToken = jwtUtil.createRefreshToken(userDetails);

        saveOrUpdateRefreshToken(member, refreshToken);



        // 3. 결과 반환
        return MemberResDTO.LoginTokenDTO.of(member, accessToken, refreshToken);
    }


    private void saveOrUpdateRefreshToken(Member member, String refreshToken){
        Optional<RefreshToken> existingTokenEntity = refreshTokenRepository.findByMemberId(member.getId());
        if(existingTokenEntity.isPresent()) {
            existingTokenEntity.get().update(refreshToken, LocalDateTime.now().plusDays(7));
        }
        else{
            RefreshToken newTokenEntity = RefreshToken.builder()
                    .member(member)
                    .refreshToken(refreshToken)
                    .expiryDate(LocalDateTime.now().plusDays(7))
                    .build();
            refreshTokenRepository.save(newTokenEntity);
        }

    }

    @Transactional
    public void logout(String refreshToken) {
        if (refreshToken != null && jwtUtil.isValid(refreshToken)) {
            refreshTokenRepository.deleteByRefreshToken(refreshToken);
        }
    }

    @Transactional
    public MemberResDTO.ReissueDTO reissueToken(String refreshToken) {
        // 1. 토큰 유효성 검사 (JWT 서명/만료)
        if (!jwtUtil.isValid(refreshToken)) {
            throw new MemberException(MemberErrorCode.TOKEN_INVALID);
        }

        // 2. 토큰 값으로 DB 조회
        RefreshToken savedToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new MemberException(MemberErrorCode.TOKEN_INVALID));

        // 3. Member 조회
        Member member = savedToken.getMember();

        // 엑세스 토큰 발급
        String accessToken = jwtUtil.createAccessToken(new CustomUserDetails(member));

        return MemberResDTO.ReissueDTO.of(accessToken);
    }

    public String getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }


}
