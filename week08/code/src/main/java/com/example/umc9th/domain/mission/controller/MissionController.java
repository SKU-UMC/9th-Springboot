package com.example.umc9th.domain.mission.controller;


import com.example.umc9th.domain.mission.dto.req.MissionReqDTO;
import com.example.umc9th.domain.mission.dto.res.MissionResDTO;
import com.example.umc9th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc9th.domain.mission.service.MissionService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
