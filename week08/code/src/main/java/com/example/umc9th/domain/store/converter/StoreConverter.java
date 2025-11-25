package com.example.umc9th.domain.store.converter;

import com.example.umc9th.domain.store.dto.res.StoreResDTO;
import com.example.umc9th.domain.store.entity.Store;
import org.springframework.data.domain.Page;

import java.util.List;

public class StoreConverter {

    public static StoreResDTO.Search toSearchDTO(Store store){
        return StoreResDTO.Search.builder()
                .storeId(store.getId())
                .name(store.getName())
                .managerNumber(store.getManagerNumber())
                .detailAddress(store.getDetailAddress())
                .locationId(store.getLocation().getId())
                .build();
    }

    public static StoreResDTO.Page<StoreResDTO.Search> toPageDTO(Page<Store> page){
        List<StoreResDTO.Search> content = page.getContent().stream().map(StoreConverter::toSearchDTO).toList();
        return StoreResDTO.Page.<StoreResDTO.Search>builder()
                .content(content)
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .build();
    }

    public static StoreResDTO.Add toAddDTO(Store store){
        return StoreResDTO.Add.builder()
                .storeId(store.getId())
                .build();
    }
}
