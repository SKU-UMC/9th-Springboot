package com.example.umc9th.domain.store.dto.res;

import com.example.umc9th.global.dto.BaseResDTO;
import lombok.Builder;
import lombok.Getter;

public class StoreResDTO extends BaseResDTO {

    @Getter
    @Builder
    public static class Search{
        private Long storeId;
        private String name;
        private Long managerNumber;
        private String detailAddress;
        private Long locationId;
    }

    @Getter
    @Builder
    public static class Add{
        private Long storeId;
    }



}
