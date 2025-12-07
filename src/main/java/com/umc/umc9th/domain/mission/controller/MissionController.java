package com.umc.umc9th.domain.mission.controller;

import com.umc.umc9th.domain.mission.dto.response.StoreMissionsSearchResponse;
import com.umc.umc9th.domain.mission.service.MissionService;
import com.umc.umc9th.domain.mission.userMission.dto.request.UserMissionStartRequest;
import com.umc.umc9th.domain.mission.userMission.dto.response.UserMissionInProgressResponse;
import com.umc.umc9th.domain.mission.userMission.dto.response.UserMissionStartResponse;
import com.umc.umc9th.domain.mission.userMission.dto.response.UserMissionSuccessResponse;
import com.umc.umc9th.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    // 9주차 특정 가게의 미션 목록
    @Operation(
            summary = "특정 가게의 미션 목록 조회",
            description = "storeId에 해당하는 가게의 미션을 페이지 단위로 조회합니다. page는 1 이상의 정수입니다."
    )
    @GetMapping("/{storeId}/missions")
    public ApiResponse<Page<StoreMissionsSearchResponse>> getStoreMissions(
            @PathVariable Long storeId,
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {
        Page<StoreMissionsSearchResponse> response =
                missionService.getStoreMissions(storeId, page);

        return ApiResponse.success(response);
    }


    // 9주차 내가 진행중인 미션 목록
    @Operation(
            summary = "내 진행중 미션 목록 조회",
            description = "userId와 page 번호를 기준으로 진행중인 유저미션들을 페이지 단위로 조회합니다."
    )
    @GetMapping("userMissions/in-progress")
    public ApiResponse<Page<UserMissionInProgressResponse>> getMyInProgressMissions(
            @Parameter(description = "유저 ID", example = "1")
            @RequestParam("userId") Long userId,

            @Parameter(description = "1 이상의 페이지 번호", example = "1")
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {

        Page<UserMissionInProgressResponse> response =
                missionService.getInProgressUserMissions(userId, page);

        return ApiResponse.success(response);
    }

    // 9주차 진행중인 미션 진행 완료로 바꾸기
    @Operation(
            summary = "진행중 유저미션 완료 처리",
            description = "진행중 상태의 유저미션을 완료 상태로 변경하고, 변경된 유저미션 정보를 반환합니다."
    )
    @PatchMapping("/userMissions/{userMissionId}/success")
    public ApiResponse<UserMissionSuccessResponse> completeUserMission(
            @Parameter(description = "유저 ID", example = "1")
            @RequestParam("userId") Long userId,

            @Parameter(description = "유저미션 ID", example = "10")
            @PathVariable Long userMissionId
    ) {
        UserMissionSuccessResponse response =
                missionService.completeUserMission(userId, userMissionId);

        return ApiResponse.success(response);
    }

}
