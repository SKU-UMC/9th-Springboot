package com.example.umc9th1.config;

import com.example.umc9th1.domain.entity.others.Location;
import com.example.umc9th1.domain.entity.others.LocationRepository;
import com.example.umc9th1.domain.enums.GenderType;
import com.example.umc9th1.domain.enums.StoreCategoryType;
import com.example.umc9th1.domain.mission.entity.Mission;
import com.example.umc9th1.domain.mission.repository.MissionRepository;
import com.example.umc9th1.domain.review.entity.Review;
import com.example.umc9th1.domain.review.repository.ReviewRepository;
import com.example.umc9th1.domain.store.entity.Store;
import com.example.umc9th1.domain.store.entity.StoreCategory;
import com.example.umc9th1.domain.store.repository.StoreCategoryRepository;
import com.example.umc9th1.domain.store.repository.StoreRepository;
import com.example.umc9th1.domain.users.entity.UserMission;
import com.example.umc9th1.domain.users.entity.Users;
import com.example.umc9th1.domain.users.repository.UserMissionRepository;
import com.example.umc9th1.domain.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
@Slf4j
public class DummyDataConfig {

    @Bean
    @Transactional
    public CommandLineRunner initDummyData(
            UserRepository usersRepository,
            StoreRepository storeRepository,
            StoreCategoryRepository storeCategoryRepository,
            LocationRepository locationRepository,
            MissionRepository missionRepository,
            UserMissionRepository userMissionRepository,
            ReviewRepository reviewRepository
    ) {
        return args -> {

            log.info(" 더미 데이터 삽입 시작");


            Location location = new Location("서울시 강남구");
            locationRepository.save(location);


            StoreCategory category = new StoreCategory(StoreCategoryType.CHINESE);
            storeCategoryRepository.save(category);


            Store store1 = new Store(
                    "스타벅스 강남점",
                    1001L,
                    "강남대로 123",
                    category,
                    location
            );
            Store store2 = new Store(
                    "스타벅스 서경대점",
                    1002L,
                    "강남대로 5678",
                    category,
                    location
            );
            storeRepository.save(store2);
            storeRepository.save(store1);


            Users user = new Users(
                    "홍길동",
                    GenderType.MALE,
                    LocalDate.of(1995, 3, 15),
                    "hong@test.com",
                    location,
                    "강남구 논현동 12-3"
            );
            usersRepository.save(user);


            Mission mission1 = new Mission(
                    100,
                    LocalDate.now().plusDays(5),
                    "아메리카노 주문하기",
                    store1
            );
            Mission mission2 = new Mission(
                    200,
                    LocalDate.now().plusDays(5),
                    "밀크티 주문하기",
                    store2
            );
            missionRepository.save(mission1);
            missionRepository.save(mission2);


            UserMission userMission1 = new UserMission(user, mission1);
            UserMission userMission2 = new UserMission(user, mission2);
            userMission1.updateSuccess();
            userMission2.updateSuccess();
            userMissionRepository.save(userMission1);
            userMissionRepository.save(userMission2);


            Review review1 = new Review(
                    4.5,
                    "데이터1 별점 4.5",
                    user,
                    store1,
                    mission1
            );
            Review review2 = new Review(
                    4.1,
                    "데이터2 별점 4.1",
                    user,
                    store1,
                    mission1
            );
            Review review3 = new Review(
                    3.2,
                    "데이터3 별점 3.2",
                    user,
                    store1,
                    mission1
            );
            Review review4 = new Review(
                    4.6,
                    "데이터 4 별점 4.6",
                    user,
                    store1,
                    mission1
            );
            Review review5 = new Review(
                    3.5,
                    "데이터 5 별점 3.5",
                    user,
                    store2,
                    mission2
            );
            Review review6 = new Review(
                    3.2,
                    "데이터 6 별점 3.2",
                    user,
                    store2,
                    mission2
            );
            reviewRepository.save(review1);
            reviewRepository.save(review2);
            reviewRepository.save(review3);
            reviewRepository.save(review4);
            reviewRepository.save(review5);
            reviewRepository.save(review6);


           log.info("더미데이터 생성 완료");
        };
    }
}
