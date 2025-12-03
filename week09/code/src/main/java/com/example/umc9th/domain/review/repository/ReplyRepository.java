package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.entity.Reply;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Reply rp WHERE rp.review.id IN :reviewIds")
    void deleteAllByReviewIdsInBatch(@Param("reviewIds") List<Long> reviewIds);
}
