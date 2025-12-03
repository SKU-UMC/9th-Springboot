package com.example.umc9th.domain.store.repository;

import com.example.umc9th.domain.store.entity.QLocation;
import com.example.umc9th.domain.store.entity.QStore;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.exception.StoreException;
import com.example.umc9th.domain.store.exception.code.StoreErrorCode;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreQueryDSLImpl implements StoreQueryDSL {

    private final EntityManager em;

    @Override
    public Page<Store> searchStores(Predicate predicate, String sort, Pageable pageable) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QStore store = QStore.store;
        QLocation location = QLocation.location;

        //3. 정렬 - 최신순, 이름순
        OrderSpecifier<?>[] order;
        if("latest".equals(sort)){
            order = new OrderSpecifier[] {store.createdAt.desc()};
        }
        else if("name".equals(sort)){
            order = new OrderSpecifier[] {
                    store.sortKey.asc(),
                    store.createdAt.desc()
            };
        }
        else{
            throw new StoreException(StoreErrorCode.SORT_NOT_FOUND);    //Exception 추가
        }

        // 4-1. 데이터 목록 조회 (OFFSET 페이징)
        List<Store> results = queryFactory
                .selectFrom(store)
                .join(store.location, location).fetchJoin()     //StoreResDTO의 locationId 응답 위해 fetchJoin
                .where(predicate)
                .orderBy(order)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 4-2. 전체 개수 조회 (카운트 쿼리)
        JPAQuery<Long> total = queryFactory
                .select(store.count())
                .from(store)
                .where(predicate);
        
        return PageableExecutionUtils.getPage(results, pageable, total::fetchOne);

//        return new PageImpl<>(results, pageable, (total != null) ? total : 0L);
    }
}
