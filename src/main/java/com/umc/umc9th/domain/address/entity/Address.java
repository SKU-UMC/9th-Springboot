package com.umc.umc9th.domain.address.entity;

import com.umc.umc9th.domain.store.entity.Store;
import com.umc.umc9th.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @Column(name = "road_address", nullable = false, length = 255)
    private String roadAddress;

    @Column(name = "detail_address", length = 255)
    private String detailAddress;

    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    private User user;

    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    private Store store;

}
