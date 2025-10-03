package com.example.umc9th.domain.mission.entity;

import com.example.umc9th.global.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "mission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // mission_id

    private LocalDateTime createdAt;
    private LocalDate finishAt;
    private Integer point;
    private LocalDate endDate;
    private String condition;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;



}
