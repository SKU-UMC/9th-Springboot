package com.example.umc9th.domain.store.controller;

import com.example.umc9th.domain.store.dto.LocationRequestDto;
import com.example.umc9th.domain.store.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/locations")
    public void addLocation(@RequestBody LocationRequestDto request) {
        locationService.addLocation(request);

    }
}
