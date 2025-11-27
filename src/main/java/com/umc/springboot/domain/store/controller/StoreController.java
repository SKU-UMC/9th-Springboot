package com.umc.springboot.domain.store.controller;

import com.umc.springboot.domain.store.dto.request.StoreRequest;
import com.umc.springboot.domain.store.dto.response.StoreResponse;
import com.umc.springboot.domain.store.entity.District;
import com.umc.springboot.domain.store.service.StoreService;
import com.umc.springboot.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
@Tag(name = "Store", description = "매장 관련 API")
public class StoreController {

  private final StoreService storeService;

  /**
   * 특정 지역에 매장 생성 API
   */
  @Operation(summary = "특정 지역에 매장 생성")
  @PostMapping("/districts/{district}")
  public ResponseEntity<BaseResponse<StoreResponse>> createStore(
      @PathVariable District district,
      @Valid @RequestBody StoreRequest request
  ) {
    StoreResponse response = storeService.createStore(district, request);
    return ResponseEntity.ok(BaseResponse.success("매장 생성 성공", response));
  }
}
