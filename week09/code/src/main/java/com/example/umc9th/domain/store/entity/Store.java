package com.example.umc9th.domain.store.entity;

import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "store")
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",  nullable = false)
    private String name;

    @Column(name = "manager_number",  nullable = false)
    private Long managerNumber;

    @Column(name = "detail_address",  nullable = false)
    private String detailAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @BatchSize(size=10)
    private Set<Mission> missionList = new HashSet<>();

    @OneToMany(mappedBy = "store")
    private Set<Review> reviewList = new HashSet<>();

    @Column(nullable = false)
    private String sortKey;

    @PrePersist @PreUpdate
    private void syncSortKey() {
        this.sortKey = buildSortKey(this.name);
    }
    public static String buildSortKey(String name) {
        if (name == null) return "";
        name = name.trim();

        StringBuilder sb = new StringBuilder(name.length() * 2);
        name.codePoints().forEach(cp -> {
            int bucket =
                    (cp >= 0xAC00 && cp <= 0xD7A3) ? 1 :                // 가~힣 (완성형)
                            (cp >= 'A' && cp <= 'Z') ? 2 :
                                    (cp >= 'a' && cp <= 'z') ? 3 : 4;
            sb.append((char) ('0' + bucket));
            // 소문자화(유니코드 안전)
            sb.append(new String(Character.toChars(Character.toLowerCase(cp))));
        });
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Store)) return false;
        Store store = (Store) o;
        return Objects.equals(getLocation(), store.getLocation());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getLocation());
    }

}
