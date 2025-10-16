    package com.example.umc9th1.domain.entity.review;

    import com.example.umc9th1.global.baseentity.BaseIdEntity;
    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.AccessLevel;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.NonNull;

    @Entity
    @Table(name = "review_photo")
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class ReviewPhoto extends BaseIdEntity {

        @Column(name = "review_photo_url",nullable = false ,length = 2000)
        private String url;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "review_id" , nullable = false)
        @JsonIgnore
        private Review review;

        public ReviewPhoto(@NonNull String url,
                           @NonNull Review review) {
            this.url = url;
            this.review = review;
        }

        public void updateUrl(String url){
            this.url = url;
        }

    }
