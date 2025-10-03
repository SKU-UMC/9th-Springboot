package com.example.umc9th.domain.home.controller;

import com.example.umc9th.domain.auth.service.AuthService;
import com.example.umc9th.domain.home.dto.HomeMissionDTO;
import com.example.umc9th.domain.home.service.HomeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;
    private final AuthService authService;

    public HomeController(HomeService homeService, AuthService authService) {
        this.homeService = homeService;
        this.authService = authService;
    }

    @GetMapping
    public List<HomeMissionDTO> getHomeMissions(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "lastCreateAt", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastCreateAt,
            @RequestParam(value = "lastMissionId", required = false) Long lastMissionId,
            @RequestParam(value = "limit", defaultValue = "5") int limit
    ) {
        Long userId = authService.getUserIdFromToken(token);
        return homeService.getHomeMissions(userId, lastCreateAt, lastMissionId, limit);
    }
}
