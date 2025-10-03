package com.example.umc9th.domain.user.entity;

import com.example.umc9th.global.entity.Term;
import com.example.umc9th.global.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_term")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // user_term_id


    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "term_id")
    private Term term;
}
