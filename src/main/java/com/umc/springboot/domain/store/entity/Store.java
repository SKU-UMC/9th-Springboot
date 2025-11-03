package com.umc.springboot.domain.store.entity;

import com.umc.springboot.domain.mission.entity.Mission;
import com.umc.springboot.domain.review.entity.Review;
import com.umc.springboot.global.common.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "store")
public class Store extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "manager_number", unique = true, nullable = false)
  private String managerNumber;

  @Enumerated(EnumType.STRING)
  @Column(name = "district", nullable = false)
  private District district;

  @Column(name = "detail_address", nullable = false)
  private String detailAddress;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "phone", nullable = false)
  private String phone;

  @Column(name = "open_time", nullable = false)
  private String openTime;

  @Column(name = "close_time", nullable = false)
  private String closeTime;

  @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
  private List<Review> reviews = new ArrayList<>();

  @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
  private List<Mission> missions = new ArrayList<>();
}