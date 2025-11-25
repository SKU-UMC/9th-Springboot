package com.example.umc9th.domain.store.service;

import com.example.umc9th.domain.store.dto.req.StoreReqDTO;
import com.example.umc9th.domain.store.entity.Location;
import com.example.umc9th.domain.store.entity.QStore;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.exception.LocationException;
import com.example.umc9th.domain.store.exception.StoreException;
import com.example.umc9th.domain.store.exception.code.LocationErrorCode;
import com.example.umc9th.domain.store.exception.code.StoreErrorCode;
import com.example.umc9th.domain.store.repository.LocationRepository;
import com.example.umc9th.domain.store.repository.StoreRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        else{
            throw new StoreException(StoreErrorCode.REGION_NOT_EXIST);  //Exception 추가
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
        else{
            throw new StoreException(StoreErrorCode.KEYWORD_NOT_EXIST); //Exception 추가
        }

        return storeRepository.searchStores(mainBuilder, sort, pageable);
    }

    @Transactional
    public Store addStore(StoreReqDTO request){
        Location location = locationRepository.findById(request.getLocationId()).orElseThrow(() -> new LocationException(LocationErrorCode.LOCATION_NOT_FOUND));
        Store store = Store.builder()
                .name(request.getName())
                .detailAddress(request.getDetailAddress())
                .managerNumber(request.getManagerNumber())
                .location(location)
                .build();
        return storeRepository.save(store);
    }
}
