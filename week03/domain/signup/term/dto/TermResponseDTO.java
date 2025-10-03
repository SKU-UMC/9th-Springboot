package com.example.umc9th.domain.signup.term.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TermResponseDTO {
    private Long id;
    private String name; // 약관명
}
