package com.example.umc9th.domain.store.dto.res;

import lombok.Builder;
import lombok.Getter;

public class StoreResDTO{

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
