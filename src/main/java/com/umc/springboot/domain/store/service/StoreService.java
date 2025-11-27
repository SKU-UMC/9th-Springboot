package com.umc.springboot.domain.store.service;

import com.umc.springboot.domain.store.converter.StoreConverter;
import com.umc.springboot.domain.store.dto.request.StoreRequest;
import com.umc.springboot.domain.store.dto.response.StoreResponse;
import com.umc.springboot.domain.store.entity.District;
import com.umc.springboot.domain.store.entity.Store;
import com.umc.springboot.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

  private final StoreRepository storeRepository;

  /**
   * 특정 지역에 매장 생성
   */
  @Transactional
  public StoreResponse createStore(District district, StoreRequest req) {

    storeRepository.findByManagerNumber(req.getManagerNumber())
        .ifPresent(s -> {
          throw new IllegalArgumentException("이미 등록된 사업자 번호입니다.");
        });

    Store store = StoreConverter.toEntity(req, district);
    storeRepository.save(store);

    return StoreConverter.toResponse(store);
  }
}
