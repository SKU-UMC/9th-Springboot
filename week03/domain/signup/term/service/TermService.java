package com.example.umc9th.domain.signup.term.service;

import com.example.umc9th.domain.signup.term.dto.TermResponseDTO;
import com.example.umc9th.domain.signup.term.repository.TermRepository;
import com.example.umc9th.global.entity.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class TermService {
    private final TermRepository termRepository;

    @Autowired
    public TermService(TermRepository termRepository) {
        this.termRepository = termRepository;
    }

    public List<TermResponseDTO> getAllTerms() {
        List<Term> termsList = termRepository.findAll();
        return termsList.stream()
                .map(t -> new TermResponseDTO(t.getId(), t.getTermName()))
                .collect(Collectors.toList());
    }
}
