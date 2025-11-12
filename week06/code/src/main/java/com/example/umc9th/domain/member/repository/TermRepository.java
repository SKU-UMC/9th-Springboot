package com.example.umc9th.domain.member.repository;

import com.example.umc9th.domain.member.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Long> {
}
