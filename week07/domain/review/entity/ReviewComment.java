package com.example.umc9th1.domain.review.entity;

import com.example.umc9th1.domain.store.entity.Store;
import com.example.umc9th1.global.baseentity.BaseIdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewComment extends BaseIdEntity {

    @Column(name = "content" ,nullable = false ,columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id" , nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id" , nullable = false)
    @JsonIgnore
    private Review review;

    public ReviewComment(@NonNull String content,
                         @NonNull Store store,
                         @NonNull Review review) {
        this.content = content;
        this.store = store;
        this.review = review;
    }
    public void updateContent(String content){
        this.content = content;
    }




}
