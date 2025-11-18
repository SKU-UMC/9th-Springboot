package com.example.umc9th.domain.member.repository;

import com.example.umc9th.domain.member.entity.mapping.MemberTerm;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberTermRepository extends JpaRepository<MemberTerm, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM MemberTerm mt WHERE mt.member.id = :memberId")
    void deleteAllByMemberIdInBatch(@Param("memberId") Long memberId);
}
