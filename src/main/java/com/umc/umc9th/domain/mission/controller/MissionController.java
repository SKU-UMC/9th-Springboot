package com.umc.umc9th.domain.mission.controller;

import com.umc.umc9th.domain.mission.service.MissionService;
import com.umc.umc9th.domain.mission.userMission.dto.request.UserMissionStartRequest;
import com.umc.umc9th.domain.mission.userMission.dto.response.UserMissionStartResponse;
import com.umc.umc9th.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Mission", description = "가게 미션 및 유저 미션 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/missions")
public class MissionController {

    private final MissionService missionService;

    /**
     * 미션 도전(시작) - 유저미션 생성
     * POST /api/v1/missions/{missionId}/stores/{storeId}/start
     */
    @Operation(
            summary = "유저 미션 등록",
            description = "가게에 관한 미션을 유저 미션으로 가져옴을 통하여 미션을 시작합니다."
    )
    @PostMapping("/{missionId}/stores/{storeId}/start")
    public ApiResponse<UserMissionStartResponse> startMission(
            @PathVariable Long storeId,
            @PathVariable Long missionId,
            @RequestBody @Valid UserMissionStartRequest request
    ) {
        UserMissionStartResponse response =
                missionService.startMission(storeId, missionId, request.userId());

        return ApiResponse.success(response);
    }
}
