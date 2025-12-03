package com.example.umc_9th_springboot.domain.mission.service;

import com.example.umc_9th_springboot.domain.mission.converter.MissionConverter;
import com.example.umc_9th_springboot.domain.mission.dto.res.MissionResDTO;
import com.example.umc_9th_springboot.domain.mission.entity.Mission;
import com.example.umc_9th_springboot.domain.mission.entity.UserMission;
import com.example.umc_9th_springboot.domain.mission.exception.MissionException;
import com.example.umc_9th_springboot.domain.mission.exception.code.MissionErrorCode;
import com.example.umc_9th_springboot.domain.mission.repository.MissionRepository;
import com.example.umc_9th_springboot.domain.mission.repository.UserMissionRepository;
import com.example.umc_9th_springboot.domain.shop.entity.Shop;
import com.example.umc_9th_springboot.domain.shop.repository.ShopRepository;
import com.example.umc_9th_springboot.domain.user.entity.User;
import com.example.umc_9th_springboot.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.umc_9th_springboot.domain.mission.enums.MissionStatus;



@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    // 완료한 미션 카운트 - 홈
    public Long getCompletedMissionCount(Long userId) {
        return userMissionRepository.countCompletedMissions(userId);
    }

    // 도전 가능한 미션 목록 조회 - 홈
    public Page<Mission> getAvailableMissions(Long regionId, Long userId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return missionRepository.findAvailableMissionsByRegion(regionId, userId, pageable);
    }

    // 미션 도전하기
    @Transactional
    public MissionResDTO.ChallengeDTO challengeMission(Long missionId) {

        Long tempUserId = 1L;

        // 유저 조회
        User user = userRepository.findById(tempUserId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // 미션 조회
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.NOT_FOUND));

        // UserMission 엔티티 생성
        UserMission userMission = MissionConverter.toUserMission(user, mission);

        // 저장
        UserMission savedUserMission = userMissionRepository.save(userMission);

        // DTO 변환 후 반환
        return MissionConverter.toChallengeDTO(savedUserMission);
    }

     //특정 가게의 미션 목록 조회
    @Transactional(readOnly = true)
    public MissionResDTO.MissionPreviewListDTO getShopMissions(Long shopId, Integer page) {

        // 가게 조회
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalArgumentException("가게를 찾을 수 없습니다."));

        // page는 1부터 들어오고, JPA는 0부터 사용 → -1
        int pageIndex = page - 1;

        PageRequest pageRequest = PageRequest.of(pageIndex, 10); //10개씩 페이징

        // 특정 가게의 미션 목록 페이징 조회
        Page<Mission> result = missionRepository.findAllByShop(shop, pageRequest);

        // Page<Mission> → MissionPreviewListDTO 변환
        return MissionConverter.toMissionPreviewListDTO(result);
    }

    @Transactional(readOnly = true)
    public MissionResDTO.ProgressMissionListDTO getProgressMissions(Long userId, Integer page) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        PageRequest pageRequest = PageRequest.of(page - 1, 10);

        // 진행중인 미션 조회
        Page<UserMission> result = userMissionRepository.findAllByUserAndStatus(
                user,  MissionStatus.PROGRESS,pageRequest
        );

        return MissionConverter.toProgressMissionListDTO(result);
    }
}
