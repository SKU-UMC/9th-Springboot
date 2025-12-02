package com.example.umc9th1.domain.review.service;

import com.example.umc9th1.domain.mission.entity.Mission;
import com.example.umc9th1.domain.mission.repository.MissionRepository;
import com.example.umc9th1.domain.review.dto.ReviewRequestDto;
import com.example.umc9th1.domain.review.dto.ReviewResponseDto;
import com.example.umc9th1.domain.review.entity.Review;
import com.example.umc9th1.domain.review.exception.ReviewException;
import com.example.umc9th1.domain.review.exception.code.ReviewErrorCode;
import com.example.umc9th1.domain.review.repository.ReviewRepository;
import com.example.umc9th1.domain.store.entity.Store;
import com.example.umc9th1.domain.users.entity.UserMission;
import com.example.umc9th1.domain.users.entity.Users;
import com.example.umc9th1.domain.users.exception.code.UserErrorCode;
import com.example.umc9th1.domain.users.repository.UserMissionRepository;
import com.example.umc9th1.domain.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MissionRepository missionRepository;
    private final UserMissionRepository  userMissionRepository;
    private final UserRepository userRepository;
    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                         MissionRepository missionRepository,
                         UserMissionRepository userMissionRepository,
                         UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.missionRepository = missionRepository;
        this.userMissionRepository = userMissionRepository;
        this.userRepository = userRepository;
    }

    //리뷰 작성
    @Transactional
    public ReviewResponseDto createReview(Long userId , Long missionId, ReviewRequestDto dto){

        UserMission userMission = userMissionRepository
                .findByUserIdAndMissionId(userId, missionId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.MISSION_ACCESS_DENIED));

        //가게 조회
        Store store = userMission.getMission().getStore();
        //유저 조회
        Users  user = userMission.getUser();
        Mission mission = userMission.getMission();
        //엔티티 채우기
        Review review = new Review(dto.getStar().doubleValue(),dto.getContent(),user,store,mission);
        //리뷰 저장
        Review savedReview = reviewRepository.save(review);

        //dto 생성
        return new ReviewResponseDto(
                savedReview.getId(),
                missionId,
                store.getId(),
                savedReview.getStar(),
                savedReview.getContent()
        );

    }
    @Transactional
    public void challengeMission(Long userId, Long missionId) {

        // 1) 미션 존재 여부 확인
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.MISSION_NOT_FOUND));

        // 2) 이미 도전 중인지 확인
        userMissionRepository.findByUserIdAndMissionId(userId, missionId)
                .ifPresent(um -> {
                    throw new ReviewException(ReviewErrorCode.MISSION_ALREADY_CHALLENGED);
                });

        // 3) 유저 조회
        //Users user = mission.getUser();
        Users user = userRepository.findById(userId)
         .orElseThrow(() -> new ReviewException(UserErrorCode.USER_NOT_FOUND));

        // 4) UserMission 생성
        UserMission userMission = new UserMission(user, mission);

        // 5) 저장
        userMissionRepository.save(userMission);
    }






}
