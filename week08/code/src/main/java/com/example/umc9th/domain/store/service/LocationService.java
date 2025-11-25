package com.example.umc9th.domain.store.service;

import com.example.umc9th.domain.store.dto.req.LocationReqDTO;
import com.example.umc9th.domain.store.entity.Location;
import com.example.umc9th.domain.store.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    @Transactional
    public Location addLocation(LocationReqDTO request){

        Location location = Location.builder()
                .name(request.getName())
                .build();

        return locationRepository.save(location);
    }

}
