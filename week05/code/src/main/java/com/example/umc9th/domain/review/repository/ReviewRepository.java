package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r.id FROM Review r WHERE r.member.id = :memberId")
    List<Long> findAllIdsByMemberId(@Param("memberId") Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Review r WHERE r.id IN :reviewIds")
    void deleteAllByIdsInBatch(@Param("reviewIds") List<Long> reviewIds);


    //1. 리뷰 작성
    Review save(Review review);


}
