package com.example.umc9th.global.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class BaseResDTO {

    @Getter
    @Builder
    public static class Page<T>{
        private List<T> content;        // 현재 페이지 데이터 리스트
        private Integer currentPage;    // 현재 페이지 번호
        private Integer totalPages;     // 총 페이지 수
        private Long totalElements;    // 총 데이터 수
        private Boolean isFirst;        // 첫 페이지 여부
        private Boolean isLast;
    }
}
