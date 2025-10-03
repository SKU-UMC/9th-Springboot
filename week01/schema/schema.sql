CREATE TABLE `region` (
  `location_id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`location_id`)
);

CREATE TABLE `abstract_store` (
  `abstract_store_id` bigint NOT NULL AUTO_INCREMENT,
  `abstract_store_name` enum('한식집','일식집','중식집','양식집') NOT NULL,
  PRIMARY KEY (`abstract_store_id`)
);

CREATE TABLE `food` (
  `food_id` bigint NOT NULL AUTO_INCREMENT,
  `food_name` enum('A','B','C') NULL,
  PRIMARY KEY (`food_id`)
);

CREATE TABLE `term` (
  `term_id` bigint NOT NULL AUTO_INCREMENT,
  `term_name` varchar(50) NULL,
  PRIMARY KEY (`term_id`)
);



CREATE TABLE `users` ( --예약어때문에 users로 변경
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `gender` enum('M','F') NOT NULL,
  `birth` date NOT NULL,
  `detail_address` varchar(100) NOT NULL,
  `social` enum('KAKAO','NAVER','FACEBOOK') NOT NULL,
  `point` int NOT NULL DEFAULT 0,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `deleted_at` datetime(6),
  `phone_num` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `location_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`),
  FOREIGN KEY (`location_id`) REFERENCES `region` (`location_id`)
);

CREATE TABLE `store` (
  `store_id` bigint NOT NULL AUTO_INCREMENT,
  `store_name` varchar(100) NOT NULL,
  `store_code` bigint NOT NULL,
  `detail_region` varchar(50) NOT NULL,
  `abstract_store_id` bigint NOT NULL,
  `location_id` bigint NOT NULL,
  PRIMARY KEY (`store_id`),
  FOREIGN KEY (`abstract_store_id`) REFERENCES `abstract_store` (`abstract_store_id`),
  FOREIGN KEY (`location_id`) REFERENCES `region` (`location_id`)
);

CREATE TABLE `mission` (
  `mission_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `finish_at` datetime(6) NULL COMMENT '기간한정이벤트 로직에 필요',
  `point` int NOT NULL,
  `end_date` date NOT NULL,
  `condition` varchar(255) NOT NULL,
  `store_id` bigint NOT NULL,
  PRIMARY KEY (`mission_id`),
  FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`)
);

CREATE TABLE `review` (
  `review_id` bigint NOT NULL AUTO_INCREMENT,
  `star` double NOT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `content` text NOT NULL,
  `user_id` bigint NOT NULL,
  `store_id` bigint NOT NULL,
  PRIMARY KEY (`review_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`)
);


CREATE TABLE `user_food` (
  `user_food_id` bigint NOT NULL AUTO_INCREMENT,
  `food_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`user_food_id`),
  FOREIGN KEY (`food_id`) REFERENCES `food` (`food_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

CREATE TABLE `user_term` (
  `user_term_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `term_id` bigint NOT NULL,
  PRIMARY KEY (`user_term_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  FOREIGN KEY (`term_id`) REFERENCES `term` (`term_id`)
);

CREATE TABLE `review_photo` (
  `review_photo_id` bigint NOT NULL AUTO_INCREMENT,
  `review_photo_url` varchar(255) NULL,
  `review_id` bigint NOT NULL,
  PRIMARY KEY (`review_photo_id`),
  FOREIGN KEY (`review_id`) REFERENCES `review` (`review_id`)
);

CREATE TABLE `review_comment` (
  `review_comment_id` bigint NOT NULL AUTO_INCREMENT,
  `content` text NULL,
  `store_id` bigint NOT NULL,
  `review_id` bigint NOT NULL,
  PRIMARY KEY (`review_comment_id`),
  FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`),
  FOREIGN KEY (`review_id`) REFERENCES `review` (`review_id`)
);

CREATE TABLE `user_mission` (
  `user_mission_id` bigint NOT NULL AUTO_INCREMENT,
  `success` tinyint(1) NOT NULL DEFAULT 0,
  `start_at` datetime(6),
  `success_at` datetime(6),
  `user_id` bigint NOT NULL,
  `mission_id` bigint NOT NULL,
  PRIMARY KEY (`user_mission_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  FOREIGN KEY (`mission_id`) REFERENCES `mission` (`mission_id`)
);