package com.example.umc9th.domain.store.service;

import com.example.umc9th.domain.store.dto.LocationRequestDto;
import com.example.umc9th.domain.store.entity.Location;
import com.example.umc9th.domain.store.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public void addLocation(LocationRequestDto request){

        Location location = Location.builder()
                .name(request.getName())
                .build();

        locationRepository.save(location);
    }

}
