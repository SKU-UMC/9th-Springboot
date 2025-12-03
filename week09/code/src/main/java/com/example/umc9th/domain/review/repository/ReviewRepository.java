package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.repository.querydsl.ReviewQueryDSL;
import com.example.umc9th.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewQueryDSL {
    @Query("SELECT r.id FROM Review r WHERE r.member.id = :memberId")
    List<Long> findAllIdsByMemberId(@Param("memberId") Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Review r WHERE r.id IN :reviewIds")
    void deleteAllByIdsInBatch(@Param("reviewIds") List<Long> reviewIds);


    Page<Review> findAllByStore(Store store, PageRequest pageRequest);

    Page<Review> findByMemberId(Long memberId, Pageable pageable);
}
