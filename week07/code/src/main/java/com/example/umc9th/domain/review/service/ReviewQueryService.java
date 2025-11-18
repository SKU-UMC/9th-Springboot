package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.review.entity.QReview;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.domain.store.entity.QLocation;
import com.example.umc9th.domain.store.entity.QStore;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewQueryService {
    private final ReviewRepository reviewRepository;

    public List<Review> searchReview(String query, String type) {
        QReview review = QReview.review;
        QLocation location = QLocation.location;
        BooleanBuilder builder = new BooleanBuilder();

        if(type.equals("location")){
            builder.and(location.name.contains(query));
        }
        if(type.equals("star")){
            builder.and(review.star.goe(Float.parseFloat(query)));
        }
        if(type.equals("both")){
            String firstQuery = query.split("&")[0];
            String secondQuery = query.split("&")[1];

            builder.and(location.name.contains(firstQuery));
            builder.and(review.star.goe(Float.parseFloat(secondQuery)));
        }

        List<Review> reviewList = reviewRepository.searchReview(builder);
        return reviewList;
    }

    public List<Review> memberReviews(Long memberId, String query, String type) {
        QReview review = QReview.review;
        QStore store = QStore.store;

        BooleanBuilder builder = new BooleanBuilder();

        if(type.equals("store")){
            builder.and(store.name.eq(query));
        }
        if(type.equals("star")){
            builder.and(review.star.goe(Float.parseFloat(query))); // 4.0 <= star < 5.0
            builder.and(review.star.lt(Float.parseFloat(query) + 1.0));
        }
        if(type.equals("both")){    //query=송강루&4
            String firstQuery = query.split("&")[0];
            String secondQuery = query.split("&")[1];

            builder.and(store.name.eq(firstQuery));
            builder.and(review.star.goe(Float.parseFloat(secondQuery)));
            builder.and(review.star.lt(Float.parseFloat(secondQuery) + 1.0));

        }

        builder.and(review.member.id.eq(memberId));

        List<Review> reviewList = reviewRepository.memberReviews(builder);
        return reviewList;
    }



}
