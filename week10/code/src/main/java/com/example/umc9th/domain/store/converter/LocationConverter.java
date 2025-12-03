package com.example.umc9th.domain.store.converter;

import com.example.umc9th.domain.store.dto.res.LocationResDTO;
import com.example.umc9th.domain.store.entity.Location;

public class LocationConverter {

    public static LocationResDTO.Add toAddDTO(Location location){
        return LocationResDTO.Add.builder()
                .locationId(location.getId())
                .build();
    }
}
