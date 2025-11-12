package com.example.umc9th.domain.store.service;

import com.example.umc9th.domain.store.dto.StoreRequestDto;
import com.example.umc9th.domain.store.entity.Location;
import com.example.umc9th.domain.store.entity.QStore;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.repository.LocationRepository;
import com.example.umc9th.domain.store.repository.StoreRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final LocationRepository locationRepository;

    public Page<Store> searchStores(List<String> region, String keyword, String sort, Pageable pageable) {
        QStore store = QStore.store;

        BooleanBuilder mainBuilder = new BooleanBuilder();

        //1. 필터링 - 지역 기반
        if (region != null && !region.isEmpty()) {
            BooleanBuilder regionBuilder = new BooleanBuilder();
            for(String rg : region){
                regionBuilder.or(store.detailAddress.contains(rg));
            }
            mainBuilder.and(regionBuilder);
        }

        //2. 검색 - 공백 기준
        if(StringUtils.hasText(keyword)){
            BooleanBuilder keywordBuilder = new BooleanBuilder();
            String[] words = keyword.split("\\s+");
            for(String word : words){
                keywordBuilder.or(store.name.contains(word));
            }
            mainBuilder.and(keywordBuilder);
        }

        return storeRepository.searchStores(mainBuilder, sort, pageable);
    }

    public void addStore(StoreRequestDto request){
        Location location = locationRepository.findById(request.getLocationId()).orElse(null);
        if(location != null){
            Store store = Store.builder()
                    .name(request.getName())
                    .detailAddress(request.getDetailAddress())
                    .managerNumber(request.getManagerNumber())
                    .location(location)
                    .build();
            storeRepository.save(store);
        }
    }
}
