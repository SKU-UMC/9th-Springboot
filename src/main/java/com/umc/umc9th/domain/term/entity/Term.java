package com.umc.umc9th.domain.term.entity;

import com.umc.umc9th.domain.user.userTerm.entity.UserTerm;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "term")
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_id")
    private Long id;

    @Column(name = "term_name", nullable = false, length = 150)
    private String termName;

    @Lob
    @Column(name = "term_text", nullable = false, columnDefinition = "TEXT")
    private String termText;

    @Enumerated(EnumType.STRING)
    @Column(name = "term_type", nullable = false, length = 40)
    private TermType termType;

    @OneToMany(mappedBy = "term", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<UserTerm> userTerms = new ArrayList<>();
}
