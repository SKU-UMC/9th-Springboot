package com.example.umc9th1.domain.store.repository;

import com.example.umc9th1.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}
