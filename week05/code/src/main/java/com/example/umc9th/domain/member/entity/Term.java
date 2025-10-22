package com.example.umc9th.domain.member.entity;

import com.example.umc9th.domain.member.entity.mapping.MemberTerm;
import com.example.umc9th.domain.member.enums.TermName;
import com.example.umc9th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "term")
public class Term extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TermName name = TermName.NONE;

    @OneToMany(mappedBy = "term")
    private Set<MemberTerm> memberTermList = new HashSet<>();
}
