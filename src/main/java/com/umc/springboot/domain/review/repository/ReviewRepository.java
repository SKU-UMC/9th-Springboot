package com.umc.springboot.domain.review.repository;

import com.umc.springboot.domain.review.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewQueryRepository {

  // 특정 가게의 리뷰 목록 조회
  List<Review> findByStoreId(Long storeId);

  // 특정 유저의 리뷰 목록 조회
  List<Review> findByUserId(Long userId);

  boolean existsByUserIdAndStoreId(Long userId, Long storeId);

}
