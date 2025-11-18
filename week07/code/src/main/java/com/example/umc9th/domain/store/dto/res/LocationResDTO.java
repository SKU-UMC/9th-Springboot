package com.example.umc9th.domain.store.dto.res;

import lombok.Builder;
import lombok.Getter;

public class LocationResDTO {

    @Getter
    @Builder
    public static class Add{
        private Long locationId;
    }
}
