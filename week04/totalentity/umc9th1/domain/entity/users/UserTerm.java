package com.example.umc9th1.domain.entity.users;

import com.example.umc9th1.domain.entity.others.Term;
import com.example.umc9th1.global.baseentity.BaseIdEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "user_term")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserTerm extends BaseIdEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "term_id" , nullable = false)
    private Term term;

    public UserTerm(@NonNull Users user,
                    @NonNull Term term) {
        this.user = user;
        this.term = term;
    }
}
