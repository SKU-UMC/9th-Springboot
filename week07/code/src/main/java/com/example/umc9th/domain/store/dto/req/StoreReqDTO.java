package com.example.umc9th.domain.store.dto.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreReqDTO {
    private String name;
    private Long managerNumber;
    private String detailAddress;
    private Long locationId;

}
