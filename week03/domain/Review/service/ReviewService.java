package com.example.umc9th.domain.Review.service;
import com.example.umc9th.domain.Review.dto.ReviewCreateDTO;
import com.example.umc9th.domain.Review.dto.ReviewResponseDTO;
import com.example.umc9th.domain.Review.dto.ReviewWriteDTO;
import com.example.umc9th.domain.Review.entity.Review;
import com.example.umc9th.domain.Review.repository.ReviewRepository;
import com.example.umc9th.domain.mission.repository.UserMissionRepository;
import com.example.umc9th.domain.store.StoreRepository;
import com.example.umc9th.domain.user.entity.UserMission;
import com.example.umc9th.domain.user.service.UserService;
import com.example.umc9th.global.entity.Store;
import com.example.umc9th.global.entity.Users;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final StoreRepository storeRepository;
    private final UserMissionRepository userMissionRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                         UserService userService,
                         StoreRepository storeRepository,
                         UserMissionRepository userMissionRepository) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.storeRepository = storeRepository;
        this.userMissionRepository = userMissionRepository;
    }

    // GET: 리뷰 작성 페이지 데이터
    public ReviewWriteDTO getReviewWriteData(Long userId , Long missionId) {
        Optional<UserMission> userMissionOpt = userMissionRepository
                .findByUserIdAndMissionIdAndSuccessTrue(userId , missionId);

        UserMission userMission = userMissionOpt
                .orElseThrow(() -> new IllegalArgumentException("해당 미션을 완료한 유저가 없습니다."));

        Store store = userMission.getMission().getStore();

        return new ReviewWriteDTO(
                missionId,
                store.getId(),
                store.getStoreName()
        );
    }

    // POST: 리뷰 등록
    @Transactional
    public ReviewResponseDTO createReview(Long userId, Long missionId, ReviewCreateDTO dto) {
        Optional<Users> ReviewUser = userService.getUserById(userId);

        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("해당 가게를 찾을 수 없습니다."));

        Users user = userService.getUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        // 리뷰 엔티티 생성
        Review review = new Review(user, store, dto.getStar(), dto.getContent());
        reviewRepository.save(review);

        return new ReviewResponseDTO(review.getId(), "리뷰 작성 완료");
    }
}
