package com.example.umc9th.domain.store.controller;

import com.example.umc9th.domain.store.converter.StoreConverter;
import com.example.umc9th.domain.store.dto.req.StoreReqDTO;
import com.example.umc9th.domain.store.dto.res.StoreResDTO;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.service.StoreService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/stores/search")
    public ApiResponse<StoreResDTO.Page<StoreResDTO.Search>> searchStore(@RequestParam List<String> region,
                                                      @RequestParam String keyword,
                                                      @RequestParam String sort, Pageable pageable) {
        //요청URL 예시: /stores/search?region=강남구&keyword=민트 초코&sort=name&page=0&size=10
        //다중검색: ?region=강남구&region=도봉구&region=영등포구
        Page<Store> result = storeService.searchStores(region, keyword, sort, pageable);

        StoreResDTO.Page<StoreResDTO.Search> resultDTO = StoreConverter.toPageDTO(result);
        GeneralSuccessCode code = GeneralSuccessCode.OK;

        return ApiResponse.onSuccess(code, resultDTO);
    }

    //가게 추가 API
    @PostMapping("/stores")
    public ApiResponse<StoreResDTO.Add> addStore(@RequestBody StoreReqDTO request) {
        Store result = storeService.addStore(request);

        StoreResDTO.Add resultDTO = StoreConverter.toAddDTO(result);
        GeneralSuccessCode code = GeneralSuccessCode.OK;

        return ApiResponse.onSuccess(code, resultDTO);

    }
}
