package com.umc.umc9th.domain.store.repository;

import com.umc.umc9th.domain.review.entity.Review;
import com.umc.umc9th.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Long searchStoreByStoreName(String storeName);

    Long searchById(Long id);

}
