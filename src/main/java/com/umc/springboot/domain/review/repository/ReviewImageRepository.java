package com.umc.springboot.domain.review.repository;

import com.umc.springboot.domain.review.entity.ReviewImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {

  List<ReviewImage> findByReviewIdIn(List<Long> reviewIds);
}
