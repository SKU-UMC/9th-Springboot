package com.example.umc9th1.global.baseentity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseIdEntity {
    @Id
    @Column(name = "id" , updatable = false , nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
