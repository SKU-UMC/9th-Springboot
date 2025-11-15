package com.example.umc_9th_springboot.domain.review.repository;

import com.example.umc_9th_springboot.domain.review.entity.Review;
import com.example.umc_9th_springboot.domain.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> , ReviewQueryRepository{
    //-> 단순 crud로 표현할 수 없을 때, 무조건 함수를 선언
    // 복합 조건이 이에 해당 (검색 조건이 여러 개인 경우)

    //fetch join을 사용하는 이유
    //-> N+1 문제를 방지하면서, 리뷰와 댓글을 한 번의 쿼리로 효율적으로 조회

    // 점포별 리뷰 + 리뷰 댓글 함께 조회
    @Query("select distinct r from Review r " +
            "join fetch r.user " +
            "left join fetch r.reviewCommentList " +
            "where r.shop = :shop " +
            "order by r.createdAt desc")
    List<Review> findAllByShopWithComments(Shop shop);
}
