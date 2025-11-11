package com.example.umc9th1.domain.review.entity;

import com.example.umc9th1.domain.mission.entity.Mission;
import com.example.umc9th1.domain.store.entity.Store;
import com.example.umc9th1.domain.users.entity.Users;
import com.example.umc9th1.global.baseentity.BaseIdAndTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseIdAndTimeEntity {

    @Column(name = "star" ,nullable = false , precision = 2 ,  scale = 1)
    private BigDecimal star;

    @Column(name = "content" , nullable = false , columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    @JsonIgnore
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id" , nullable = false)
    private Mission mission;


    @OneToMany(mappedBy = "review" , cascade = CascadeType.REMOVE)
    private List<ReviewComment>  reviewCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "review" , cascade = CascadeType.REMOVE)
    private List<ReviewPhoto> reviewPhotoList = new ArrayList<>();

    public Review(double star,
                  @NonNull String content,
                  @NonNull Users user,
                  @NonNull Store store,
                  @NonNull Mission mission){
        this.star=BigDecimal.valueOf(star);
        this.content=content;
        this.user = user;
        this.store = store;
        this.mission = mission;
    }

    public void updateContent(String content) {
        this.content = content;
    }




}
