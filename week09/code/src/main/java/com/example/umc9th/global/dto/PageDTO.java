package com.example.umc9th.global.dto;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public record PageDTO<T>(
        List<T> content,
        int currentPage,
        int size,
        long totalElements,
        int totalPages,
        boolean isFirst,
        boolean isLast
){

    /**
     * Page&lt;T&gt;를 PageDTO&lt;T&gt;로 변환합니다.
     * <p>
     * 주로 DTO객체를 페이징하기 위해 사용합니다.
     * </p>
     *
     * @param pageData Page 객체
     * @param <T>      데이터의 타입
     * @return 페이징 정보가 담긴 PageDTO
     */
    public static <T> PageDTO<T> of(Page<T> pageData) {
        return new PageDTO<>(
                pageData.getContent(),
                pageData.getNumber() + 1,   //page 1부터 시작
                pageData.getSize(),
                pageData.getTotalElements(),
                pageData.getTotalPages(),
                pageData.isFirst(),
                pageData.isLast()
        );
    }

    /**
     * Page&lt;Entity&gt;를 PageDTO&lt;DTO&gt;로 변환합니다. </p>
     *
     * Page&lt;Entity&gt;의 내용을 Converter를 통해 DTO 타입으로 바꾼 뒤 PageDTO&lt;DTO&gt;을 생성합니다.  </p>
     *
     * @param pageData  Page 객체
     * @param converter 리스트 내부의 각 요소를 변환할 함수 (사용 예: DomainConverter::toDto)
     * @param <E>       (Entity) 원본 데이터 타입
     * @param <R>       (Result) 변환 후 반환할 데이터 타입
     * @return 변환된 데이터 리스트와 페이징 정보가 담긴 PageDTO
     */
    public static <E, R> PageDTO<R> of(Page<E> pageData, Function<E, R> converter) {
        //Function<E, R> converter: Entity -> Converter -> Result
        List<R> content = pageData.getContent().stream().map(converter).toList();

        return new PageDTO<>(
                content,
                pageData.getNumber() + 1,   //page 1부터 시작
                pageData.getSize(),
                pageData.getTotalElements(),
                pageData.getTotalPages(),
                pageData.isFirst(),
                pageData.isLast()
        );
    }
}
