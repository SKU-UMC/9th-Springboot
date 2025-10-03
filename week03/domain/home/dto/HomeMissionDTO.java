package com.example.umc9th.domain.home.dto;

import com.example.umc9th.domain.enums.StoreType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HomeMissionDTO {

    private String regionAddress;
    private Long successCount;
    private StoreType abstractStoreName;
    private Long missionId;
    private String storeName;
    private String missionCondition;
    private Integer point;
    private LocalDate endDate;
    private LocalDateTime createdAt;


}
