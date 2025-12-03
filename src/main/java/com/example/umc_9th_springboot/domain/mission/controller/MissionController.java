package com.example.umc_9th_springboot.domain.mission.controller;

import com.example.umc_9th_springboot.domain.mission.dto.res.MissionResDTO;
import com.example.umc_9th_springboot.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc_9th_springboot.domain.mission.service.MissionService;
import com.example.umc_9th_springboot.global.apiPayload.ApiResponse;
import com.example.umc_9th_springboot.global.validation.ValidPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Mission", description = "미션 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/missions")
public class MissionController {

    private final MissionService missionService;

    // 가게의 미션을 도전 중인 미션에 추가
    @Operation(
            summary = "미션 도전하기 API",
            description = "missionId에 해당하는 미션을 현재 사용자(User)의 진행중 미션으로 추가합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "미션 도전 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "미션 또는 유저를 찾을 수 없음"
            )
    })
    @PostMapping("/{missionId}/challenge")
    public ApiResponse<MissionResDTO.ChallengeDTO> challengeMission(
            @PathVariable Long missionId
    ) {
        return ApiResponse.onSuccess(
                MissionSuccessCode.CHALLENGE_CREATED,
                missionService.challengeMission(missionId)
        );
    }

    //특정 가게의 미션 목록 조회
    @Operation(
            summary = "특정 가게의 미션 목록 조회 API",
            description = "shopId 기준으로 해당 가게의 미션을 10개씩 페이징 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "가게 미션 목록 조회 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "page 값이 1 미만 (ValidPage 검증 실패)"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "가게를 찾을 수 없음"
            )
    })
    @GetMapping("/{shopId}/missions")
    public ApiResponse<MissionResDTO.MissionPreviewListDTO> getShopMissions(
            @PathVariable Long shopId,
            @RequestParam(name = "page") @ValidPage Integer page
    ) {
        return ApiResponse.onSuccess(
                MissionSuccessCode.FOUND,
                missionService.getShopMissions(shopId, page)
        );
    }

    //내가 진행중인 미션 목록 조회
    @Operation(
            summary = "내가 진행중인 미션 목록 조회 API",
            description = "userId 기준으로 진행 상태(PROGRESS)인 미션을 10개씩 페이징 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "진행중 미션 목록 조회 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "page 값이 1 미만 (ValidPage 검증 실패)"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "유저를 찾을 수 없음"
            )
    })
    @GetMapping("/progress")
    public ApiResponse<MissionResDTO.ProgressMissionListDTO> getProgressMissions(
            @RequestParam Long userId,
            @RequestParam(name = "page") @ValidPage Integer page
    ) {
        return ApiResponse.onSuccess(
                MissionSuccessCode.FOUND_PROGRESS,
                missionService.getProgressMissions(userId, page)
        );
    }
}
