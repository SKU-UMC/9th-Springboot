package com.example.umc9th.domain.mission.controller;

import com.example.umc9th.domain.auth.service.AuthService;
import com.example.umc9th.domain.mission.dto.MissionCompleteDTO;
import com.example.umc9th.domain.mission.dto.MissionDTO;
import com.example.umc9th.domain.mission.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/missions")
public class MissionController {

    private final MissionService missionService;
    private final AuthService authService;

    @Autowired
    public MissionController(MissionService missionService, AuthService authService) {
        this.missionService = missionService;
        this.authService = authService;
    }
    //진행중
    @GetMapping("/ongoing")
    public List<MissionDTO> getOngoingMissions(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "lastCreateAt", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastCreateAt,
            @RequestParam(value = "lastMissionId", required = false) Long lastMissionId,
            @RequestParam(value = "limit" , defaultValue = "5") int  limit
    ) {
        Long userId = authService.getUserIdFromToken(token);
        return missionService.getOngoingMissions(userId, lastCreateAt, lastMissionId , limit);
    }
    //진행완료
    @GetMapping("/complete")
    public List<MissionDTO> getCompleteMissions(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "lastCreateAt", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastCreateAt,
            @RequestParam(value = "lastMissionId", required = false) Long lastMissionId,
            @RequestParam(value = "limit" , defaultValue = "5") int  limit
    ) {
        Long userId = authService.getUserIdFromToken(token);
        return missionService.getCompleteMissions(userId, lastCreateAt, lastMissionId , limit);
    }

    // 미션 성공 처리 (성공요청  클릭 시 호출)
    @PostMapping("/{missionId}/complete")
    public MissionCompleteDTO completeMission(
            @RequestHeader("Authorization") String token,
            @PathVariable Long missionId
    ) {
        Long userId = authService.getUserIdFromToken(token);
        // 성공 처리
       return missionService.completeMission(userId, missionId);
    }
}




