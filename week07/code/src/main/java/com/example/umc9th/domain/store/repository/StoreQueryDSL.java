package com.example.umc9th.domain.store.repository;

import com.example.umc9th.domain.store.entity.Store;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreQueryDSL {
    Page<Store> searchStores(Predicate predicate, String sort, Pageable pageable);
}
