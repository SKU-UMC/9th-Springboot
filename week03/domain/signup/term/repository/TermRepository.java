package com.example.umc9th.domain.signup.term.repository;

import com.example.umc9th.global.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermRepository extends JpaRepository<Term,Long> {
}
