package com.example.umc9th.domain.member.entity.mapping;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.entity.Term;
import com.example.umc9th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "member_term")
public class MemberTerm extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "term_id")
    private Term term;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberTerm)) return false;
        MemberTerm that = (MemberTerm) o;
        return Objects.equals(getMember(), that.getMember()) &&
                Objects.equals(getTerm(), that.getTerm());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMember(), getTerm());
    }


}
