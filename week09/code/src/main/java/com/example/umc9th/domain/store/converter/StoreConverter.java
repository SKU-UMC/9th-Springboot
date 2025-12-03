package com.example.umc9th.domain.store.converter;

import com.example.umc9th.domain.store.dto.res.StoreResDTO;
import com.example.umc9th.domain.store.entity.Store;

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

//    public static BaseResDTO.PageDTO<StoreResDTO.Search> toPageDTO(Page<Store> page){
//        List<StoreResDTO.Search> content = page.getContent().stream().map(StoreConverter::toSearchDTO).toList();
//        return BaseResDTO.PageDTO.<StoreResDTO.Search>builder()
//                .content(content)
//                .currentPage(page.getNumber())
//                .totalPages(page.getTotalPages())
//                .totalElements(page.getTotalElements())
//                .isFirst(page.isFirst())
//                .isLast(page.isLast())
//                .build();
//    }

    public static StoreResDTO.Add toAddDTO(Store store){
        return StoreResDTO.Add.builder()
                .storeId(store.getId())
                .build();
    }
}
