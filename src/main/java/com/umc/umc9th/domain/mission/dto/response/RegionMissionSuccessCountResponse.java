package com.umc.umc9th.domain.mission.dto.response;

public record RegionMissionSuccessCountResponse(
        Long regionId,
        String regionName,
        Long successCount
) {}
