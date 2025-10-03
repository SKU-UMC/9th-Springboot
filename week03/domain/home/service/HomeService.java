package com.example.umc9th.domain.home.service;

import com.example.umc9th.domain.home.dto.HomeMissionDTO;
import com.example.umc9th.domain.home.repository.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HomeService {

    private final HomeRepository homeRepository;

    @Autowired
    public HomeService(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    public List<HomeMissionDTO> getHomeMissions(Long userId, LocalDateTime lastCreateAt, Long lastMissionId, int limit) {
        // 레포지토리에서 홈 미션 조회
        List<HomeMissionDTO> missions = homeRepository.findHomeMissions(userId, lastCreateAt, lastMissionId);
        // limit 적용
        if (missions.size() > limit) {
            return missions.subList(0, limit);
        }
        return missions;
    }
}
