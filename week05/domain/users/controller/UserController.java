package com.example.umc9th1.domain.users.controller;

import com.example.umc9th1.domain.users.dto.UserCompletedMissionDto;
import com.example.umc9th1.domain.users.dto.UserHomeDto;
import com.example.umc9th1.domain.users.dto.UserInfoDto;
import com.example.umc9th1.domain.users.dto.UserOngoingMissionDto;
import com.example.umc9th1.domain.users.service.UserService;
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
    public ResponseEntity<UserInfoDto> getUserInfo(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserInfo(userId)); //200ok
    }

    //진행중인 미션 get
    @GetMapping("/{userId}/missions/ongoing") //pageable 사용
    public ResponseEntity<Page<UserOngoingMissionDto>> getUnsuccessfulMissions(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        //페이징 동적 sort 추가 executedAt 기준 오래된 순 정렬
        Pageable pageable = PageRequest.of(page, size,Sort.by("executedAt").ascending());
        Page<UserOngoingMissionDto> ongoingMissions = userService.getUnsuccessfulMissions(userId, pageable);

        return ResponseEntity.ok(ongoingMissions);//200ok
    }

    //진행 완료 미션 get
    @GetMapping("/{userId}/missions/complete") //pageable 사용
    public ResponseEntity<Page<UserCompletedMissionDto>> getSuccessfulMissions(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        //페이징 동적 sort 추가함 successAt 기준 최신 순 정렬
        Pageable pageable = PageRequest.of(page, size, Sort.by("successAt").descending());
        Page<UserCompletedMissionDto> completedMissions = userService.getSuccessfulMissions(userId, pageable);

        return ResponseEntity.ok(completedMissions);//200ok
    }
        @GetMapping("/{userId}/home")
        public ResponseEntity<Page<UserHomeDto>> getUserHome(
                @PathVariable Long userId,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "5") int size
        ){
            //페이징 동적 sort 추가 endDate기준 오래된 순 정렬
            Pageable pageable = PageRequest.of(page, size,Sort.by("endDate").ascending());
            Page<UserHomeDto> userMission =  userService.getUserHome(userId, pageable);

            return ResponseEntity.ok(userMission); //200ok
        }
    }
