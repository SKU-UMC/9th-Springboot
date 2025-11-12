package com.example.umc9th.domain.store.controller;

import com.example.umc9th.domain.store.dto.StoreRequestDto;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.service.StoreService;
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
    public Page<Store> searchStore(@RequestParam List<String> region,
                                   @RequestParam String keyword,
                                   @RequestParam String sort, Pageable pageable) {
        //요청URL 예시: /stores/search?region=강남구&keyword=민트 초코&sort=name&page=0&size=10
        //다중검색: ?region=강남구&region=도봉구&region=영등포구
        return storeService.searchStores(region, keyword, sort, pageable);
    }

    @PostMapping("/stores")
    public void addStore(@RequestBody StoreRequestDto request) {
        storeService.addStore(request);

    }
}
