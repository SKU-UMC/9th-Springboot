package com.example.umc9th.global.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "store")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // store_id

    private String storeName;
    private Long storeCode;
    private String detailRegion;

    @ManyToOne
    @JoinColumn(name = "abstract_store_id")
    private AbstractStore abstractStore;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Region location;
}
