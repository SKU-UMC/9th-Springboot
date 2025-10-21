package com.example.umc9th1.domain.review.service;

import com.example.umc9th1.domain.mission.repository.MissionRepository;
import com.example.umc9th1.domain.review.dto.ReviewRequestDto;
import com.example.umc9th1.domain.review.dto.ReviewResponseDto;
import com.example.umc9th1.domain.review.entity.Review;
import com.example.umc9th1.domain.review.repository.ReviewRepository;
import com.example.umc9th1.domain.store.entity.Store;
import com.example.umc9th1.domain.users.entity.UserMission;
import com.example.umc9th1.domain.users.entity.Users;
import com.example.umc9th1.domain.users.repository.UserMissionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MissionRepository missionRepository;
    private final UserMissionRepository  userMissionRepository;
    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                         MissionRepository missionRepository,
                         UserMissionRepository userMissionRepository) {
        this.reviewRepository = reviewRepository;
        this.missionRepository = missionRepository;
        this.userMissionRepository = userMissionRepository;
    }

    //리뷰 작성
    @Transactional
    public ReviewResponseDto createReview(Long userId , Long missionId, ReviewRequestDto dto){
        //미션 확인
        if (!missionRepository.existsById(missionId)) {
            throw new EntityNotFoundException("미션을 찾을 수 없습니다.");
        }
        //유저의 미션인지 체크
        UserMission userMission = userMissionRepository.findByUserIdAndMissionId(userId, missionId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 수행한 미션이 아닙니다."));
        //가게 조회
        Store store = userMission.getMission().getStore();
        //유저 조회
        Users  user = userMission.getUser();
        //엔티티 채우기
        Review review = new Review(dto.getStar(),dto.getContent(),user,store);
        //리뷰 저장
        Review savedReview = reviewRepository.save(review);

        //dto 생성
        return new ReviewResponseDto(
                savedReview.getId(),
                missionId,
                store.getId(),
                savedReview.getStar().doubleValue(),
                savedReview.getContent()
        );
    }


}
