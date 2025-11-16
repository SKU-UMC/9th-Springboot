package com.umc.umc9th.domain.mission.dto.response;

public record AvailableMissionInRegionResponse(
        Long missionId,
        Long missionPoint,
        Long missionCost,
        Long storeId,
        String storeName,
        Integer missionDue
) {}
