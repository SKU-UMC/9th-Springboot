package com.example.umc9th.domain.mission.controller;


import com.example.umc9th.domain.mission.dto.req.MissionReqDTO;
import com.example.umc9th.domain.mission.dto.res.MissionResDTO;
import com.example.umc9th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc9th.domain.mission.service.MissionService;
import com.example.umc9th.global.annotation.ValidPage;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc9th.global.dto.PageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
public class MissionController {
    private final MissionService missionService;

    // 미션 추가 API
    @PostMapping("/missions")
    public ApiResponse<MissionResDTO.AddDTO> addMission(@RequestBody @Valid MissionReqDTO.AddDTO dto){
        MissionResDTO.AddDTO result = missionService.addMission(dto);
        MissionSuccessCode code = MissionSuccessCode.ADD_OK;
        return ApiResponse.onSuccess(code, result);
    }

    // 미션 도전 API
    @PostMapping("/missions/challenge")
    public ApiResponse<MissionResDTO.ChallengeDTO> challengeMission(@RequestBody @Valid MissionReqDTO.ChallengeDTO dto){
        MissionResDTO.ChallengeDTO result = missionService.challengeMission(dto);
        MissionSuccessCode code = MissionSuccessCode.CHALLENGE_OK;
        return ApiResponse.onSuccess(code, result);
    }

    // 특정 가게의 미션 목록 (11.25)
    @Operation(
            summary = "특정 가게의 미션 목록 조회 API (11.25)",
            description = "특정 가게의 미션 목록을 모두 조회합니다. 페이지네이션으로 제공합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/missions/{storeId}")
    public ApiResponse<PageDTO<MissionResDTO.MissionDTO>> getMissions(@PathVariable Long storeId, @Valid @ValidPage Pageable pageable){
        PageDTO<MissionResDTO.MissionDTO> result = missionService.getMissions(storeId, pageable);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, result);
    }

    // 내가 진행중인 미션 목록 (11.25)
    @Operation(
            summary = "내가 진행중인 미션 목록 조회 API (11.25)",
            description = "내가 진행중인 미션 목록을 모두 조회합니다. 페이지네이션으로 제공합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/missions/{memberId}/me")
    public ApiResponse<PageDTO<MissionResDTO.MissionDTO>> getMyMissions(@PathVariable Long memberId, @RequestParam Boolean isCompleted, @Valid @ValidPage Pageable pageable){
        PageDTO<MissionResDTO.MissionDTO> result = missionService.getMyMissions(memberId, isCompleted, pageable);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, result);
    }




}
