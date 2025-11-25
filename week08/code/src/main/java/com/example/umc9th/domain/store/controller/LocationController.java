package com.example.umc9th.domain.store.controller;

import com.example.umc9th.domain.store.converter.LocationConverter;
import com.example.umc9th.domain.store.dto.req.LocationReqDTO;
import com.example.umc9th.domain.store.dto.res.LocationResDTO;
import com.example.umc9th.domain.store.entity.Location;
import com.example.umc9th.domain.store.service.LocationService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/locations")
    public ApiResponse<LocationResDTO.Add> addLocation(@RequestBody LocationReqDTO request) {
        Location result = locationService.addLocation(request);

        LocationResDTO.Add resultDTO = LocationConverter.toAddDTO(result);
        GeneralSuccessCode code = GeneralSuccessCode.OK;

        return ApiResponse.onSuccess(code, resultDTO);
    }
}
