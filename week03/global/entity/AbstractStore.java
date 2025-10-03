package com.example.umc9th.global.entity;

import com.example.umc9th.domain.enums.StoreType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "abstract_store")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbstractStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // abstract_store_id

    @Enumerated(EnumType.STRING)
    private StoreType abstractStoreName;

}
