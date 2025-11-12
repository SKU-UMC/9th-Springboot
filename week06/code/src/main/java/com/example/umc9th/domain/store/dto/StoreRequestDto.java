package com.example.umc9th.domain.store.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreRequestDto {
    private String name;
    private Long managerNumber;
    private String detailAddress;
    private Long locationId;

}
