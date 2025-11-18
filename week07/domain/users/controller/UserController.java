package com.example.umc9th1.domain.users.controller;

import com.example.umc9th1.domain.users.dto.UserCompletedMissionDto;
import com.example.umc9th1.domain.users.dto.UserHomeDto;
import com.example.umc9th1.domain.users.dto.UserInfoDto;
import com.example.umc9th1.domain.users.dto.UserOngoingMissionDto;
import com.example.umc9th1.domain.users.service.UserService;
import com.example.umc9th1.global.apipayload.ApiResponse;
import com.example.umc9th1.global.apipayload.code.GeneralSuccessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    //유저 Info get
    @GetMapping("/{userId}/info")
    public ResponseEntity<ApiResponse<UserInfoDto>> getUserInfo(@PathVariable("userId") Long userId) {
        UserInfoDto userInfo = userService.getUserInfo(userId);
        GeneralSuccessCode code = GeneralSuccessCode.SUCCESS;
        return ResponseEntity.status(code.getStatus()).body(ApiResponse.onSuccess(code,userInfo)); //200ok
    }

    //진행중인 미션 get
    @GetMapping("/{userId}/missions/ongoing") //pageable 사용
    public ResponseEntity<ApiResponse<Page<UserOngoingMissionDto>>> getUnsuccessfulMissions(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        //페이징 동적 sort 추가 executedAt 기준 오래된 순 정렬
        Pageable pageable = PageRequest.of(page, size,Sort.by("executedAt").ascending());
        Page<UserOngoingMissionDto> ongoingMissions = userService.getUnsuccessfulMissions(userId, pageable);
        GeneralSuccessCode code = GeneralSuccessCode.SUCCESS;
        return ResponseEntity.status(code.getStatus()).body(ApiResponse.onSuccess(code,ongoingMissions));//200ok
    }

    //진행 완료 미션 get
    @GetMapping("/{userId}/missions/complete") //pageable 사용
    public ResponseEntity<ApiResponse<Page<UserCompletedMissionDto>>> getSuccessfulMissions(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        //페이징 동적 sort 추가함 successAt 기준 최신 순 정렬
        Pageable pageable = PageRequest.of(page, size, Sort.by("successAt").descending());
        Page<UserCompletedMissionDto> completedMissions = userService.getSuccessfulMissions(userId, pageable);
        GeneralSuccessCode code = GeneralSuccessCode.SUCCESS;
        return ResponseEntity.status(code.getStatus()).body(ApiResponse.onSuccess(code,completedMissions));//200ok
    }
    //유저 홈
        @GetMapping("/{userId}/home")
        public ResponseEntity<ApiResponse<Page<UserHomeDto>>> getUserHome(
                @PathVariable Long userId,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "5") int size
        ){
            //페이징 동적 sort 추가 endDate기준 오래된 순 정렬
            Pageable pageable = PageRequest.of(page, size,Sort.by("endDate").ascending());
            Page<UserHomeDto> userMission =  userService.getUserHome(userId, pageable);
            GeneralSuccessCode code = GeneralSuccessCode.SUCCESS;
            return ResponseEntity.status(code.getStatus()).body(ApiResponse.onSuccess(code,userMission)); //200ok
        }
    }
