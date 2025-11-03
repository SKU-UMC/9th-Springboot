package com.umc.springboot.domain.mission.entity;

import com.umc.springboot.domain.store.entity.Store;
import com.umc.springboot.global.common.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
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
@Table(name = "mission")
public class Mission extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "store_id", nullable = true)
  private Store store;

  @Column(name = "title", nullable = false, length = 50)
  private String title;

  @Column(name = "description", nullable = false, length = 100)
  private String description;

  @Column(name = "deadline")
  private LocalDate deadline;

  @Column(name = "point")
  private String point;

  @Column(name = "date", nullable = false)
  private LocalDate date;

  @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
  private List<UserMission> userMissions = new ArrayList<>();
}