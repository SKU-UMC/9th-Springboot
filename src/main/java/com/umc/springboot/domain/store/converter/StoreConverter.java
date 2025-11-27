package com.umc.springboot.domain.store.converter;

import com.umc.springboot.domain.store.dto.request.StoreRequest;
import com.umc.springboot.domain.store.dto.response.StoreResponse;
import com.umc.springboot.domain.store.entity.District;
import com.umc.springboot.domain.store.entity.Store;

public class StoreConverter {

  public static Store toEntity(StoreRequest req, District district) {
    return Store.builder()
        .name(req.getName())
        .managerNumber(req.getManagerNumber())
        .district(district)
        .detailAddress(req.getDetailAddress())
        .description(req.getDescription())
        .phone(req.getPhone())
        .openTime(req.getOpenTime())
        .closeTime(req.getCloseTime())
        .build();
  }

  public static StoreResponse toResponse(Store store) {
    return StoreResponse.builder()
        .id(store.getId())
        .name(store.getName())
        .managerNumber(store.getManagerNumber())
        .district(store.getDistrict())
        .detailAddress(store.getDetailAddress())
        .description(store.getDescription())
        .phone(store.getPhone())
        .openTime(store.getOpenTime())
        .closeTime(store.getCloseTime())
        .build();
  }
}
