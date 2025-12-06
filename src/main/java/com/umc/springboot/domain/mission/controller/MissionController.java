package com.umc.springboot.domain.mission.controller;

import com.umc.springboot.domain.mission.dto.request.MissionCreateRequest;
import com.umc.springboot.domain.mission.dto.request.UserMissionUpdateRequest;
import com.umc.springboot.domain.mission.dto.response.MissionResponse;
import com.umc.springboot.domain.mission.dto.response.UserMissionResponse;
import com.umc.springboot.domain.mission.service.MissionService;
import com.umc.springboot.global.response.BaseResponse;
import com.umc.springboot.global.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/missions")
@Tag(name = "Mission", description = "미션 관련 API")
public class MissionController {

  private final MissionService missionService;

  /**
   * 가게에 미션 추가
   */
  @Operation(summary = "가게에 미션 추가")
  @PostMapping(
      value = "/stores/{storeId}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<BaseResponse<MissionResponse>> createMissionForStore(
      @PathVariable Long storeId,
      @Valid @RequestBody MissionCreateRequest request
  ) {
    Long userId = SecurityUtil.getCurrentUserId();

    MissionResponse response = missionService.createMissionForStore(storeId, request);
    return ResponseEntity.ok(BaseResponse.success("미션 생성에 성공했습니다.", response));
  }

  /**
   * 미션 도전하기 (도전 중인 미션에 추가)
   */
  @Operation(summary = "미션 도전하기")
  @PostMapping(value = "/{missionId}/challenge", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BaseResponse<UserMissionResponse>> challengeMission(
      @PathVariable Long missionId
  ) {
    Long userId = SecurityUtil.getCurrentUserId();
    UserMissionResponse response = missionService.challengeMission(userId, missionId);
    return ResponseEntity.ok(BaseResponse.success("미션 도전에 성공했습니다.", response));
  }

  /**
   * 특정 가게의 미션 목록 조회
   */
  @Operation(summary = "특정 가게의 미션 목록 조회")
  @GetMapping(value = "/stores/{storeId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BaseResponse<List<MissionResponse>>> getMissionsByStore(
      @PathVariable Long storeId
  ) {
    List<MissionResponse> responses = missionService.getMissionsByStore(storeId);
    return ResponseEntity.ok(BaseResponse.success("가게 미션 목록 조회에 성공했습니다.", responses));
  }

  /**
   * 내가 진행중인 미션 목록 조회
   */
  @Operation(summary = "내가 진행중인 미션 목록 조회")
  @GetMapping(value = "/me/in-progress", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BaseResponse<List<UserMissionResponse>>> getMyInProgressMissions() {
    Long userId = SecurityUtil.getCurrentUserId();
    List<UserMissionResponse> responses = missionService.getInProgressMissionsByUser(userId);
    return ResponseEntity.ok(BaseResponse.success("진행중인 미션 목록 조회에 성공했습니다.", responses));
  }

  /**
   * 진행 중인 미션 완료 처리
   */
  @PatchMapping(
      value = "/user-missions/{userMissionId}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @Operation(summary = "유저 미션 상태 변경")
  public ResponseEntity<BaseResponse<UserMissionResponse>> updateUserMissionStatus(
      @PathVariable Long userMissionId,
      @RequestBody UserMissionUpdateRequest request
  ) {
    Long userId = SecurityUtil.getCurrentUserId();
    UserMissionResponse response =
        missionService.updateUserMissionStatus(userId, userMissionId, request.getIsCompleted());

    return ResponseEntity.ok(BaseResponse.success("유저 미션 상태 변경에 성공했습니다.", response));
  }
}

