CREATE TABLE food
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    food_name VARCHAR(20)           NOT NULL,
    CONSTRAINT pk_food PRIMARY KEY (id)
);

CREATE TABLE location
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    address VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_location PRIMARY KEY (id)
);

CREATE TABLE mission
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    create_at  datetime              NOT NULL,
    change_at  datetime              NULL,
    delete_at  datetime              NULL,
    point      INT                   NOT NULL,
    end_date   date                  NOT NULL,
    conditions VARCHAR(254)          NOT NULL,
    store_id   BIGINT                NOT NULL,
    CONSTRAINT pk_mission PRIMARY KEY (id)
);

CREATE TABLE review
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    create_at datetime              NOT NULL,
    change_at datetime              NULL,
    delete_at datetime              NULL,
    star      DECIMAL(2, 1)         NOT NULL,
    content   TEXT                  NOT NULL,
    user_id   BIGINT                NOT NULL,
    store_id  BIGINT                NOT NULL,
    CONSTRAINT pk_review PRIMARY KEY (id)
);

CREATE TABLE review_comment
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    content   TEXT                  NOT NULL,
    store_id  BIGINT                NOT NULL,
    review_id BIGINT                NOT NULL,
    CONSTRAINT pk_review_comment PRIMARY KEY (id)
);

CREATE TABLE review_photo
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    review_photo_url VARCHAR(2000)         NOT NULL,
    review_id        BIGINT                NOT NULL,
    CONSTRAINT pk_review_photo PRIMARY KEY (id)
);

CREATE TABLE store
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    create_at         datetime              NOT NULL,
    change_at         datetime              NULL,
    delete_at         datetime              NULL,
    store_name        VARCHAR(100)          NOT NULL,
    store_code        BIGINT                NOT NULL,
    detail_address    VARCHAR(254)          NOT NULL,
    store_category_id BIGINT                NOT NULL,
    location_id       BIGINT                NOT NULL,
    CONSTRAINT pk_store PRIMARY KEY (id)
);

CREATE TABLE store_category
(
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    store_category_name VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_store_category PRIMARY KEY (id)
);

CREATE TABLE term
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    term_name VARCHAR(20)           NOT NULL,
    CONSTRAINT pk_term PRIMARY KEY (id)
);

CREATE TABLE user_food
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    user_id BIGINT                NOT NULL,
    food_id BIGINT                NOT NULL,
    CONSTRAINT pk_user_food PRIMARY KEY (id)
);

CREATE TABLE user_mission
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    success     BIT(1) DEFAULT 0      NOT NULL,
    executed_at datetime              NOT NULL,
    success_at  datetime              NULL,
    user_id     BIGINT                NOT NULL,
    mission_id  BIGINT                NOT NULL,
    CONSTRAINT pk_user_mission PRIMARY KEY (id)
);

CREATE TABLE user_term
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    user_id BIGINT                NOT NULL,
    term_id BIGINT                NOT NULL,
    CONSTRAINT pk_user_term PRIMARY KEY (id)
);

CREATE TABLE users
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    create_at      datetime              NOT NULL,
    change_at      datetime              NULL,
    delete_at      datetime              NULL,
    name           VARCHAR(100)          NOT NULL,
    gender         VARCHAR(255)          NOT NULL,
    birth          date                  NOT NULL,
    detail_address VARCHAR(254)          NOT NULL,
    social         VARCHAR(255)          NULL,
    point          INT                   NOT NULL,
    phone_num      VARCHAR(20)           NULL,
    email          VARCHAR(254)          NOT NULL,
    location_id    BIGINT                NOT NULL,
    status         VARCHAR(255)          NOT NULL,
    inactive_date  datetime              NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE store
    ADD CONSTRAINT uc_store_store_code UNIQUE (store_code);

ALTER TABLE mission
    ADD CONSTRAINT FK_MISSION_ON_STORE FOREIGN KEY (store_id) REFERENCES store (id);

ALTER TABLE review_comment
    ADD CONSTRAINT FK_REVIEW_COMMENT_ON_REVIEW FOREIGN KEY (review_id) REFERENCES review (id);

ALTER TABLE review_comment
    ADD CONSTRAINT FK_REVIEW_COMMENT_ON_STORE FOREIGN KEY (store_id) REFERENCES store (id);

ALTER TABLE review
    ADD CONSTRAINT FK_REVIEW_ON_STORE FOREIGN KEY (store_id) REFERENCES store (id);

ALTER TABLE review
    ADD CONSTRAINT FK_REVIEW_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE review_photo
    ADD CONSTRAINT FK_REVIEW_PHOTO_ON_REVIEW FOREIGN KEY (review_id) REFERENCES review (id);

ALTER TABLE store
    ADD CONSTRAINT FK_STORE_ON_LOCATION FOREIGN KEY (location_id) REFERENCES location (id);

ALTER TABLE store
    ADD CONSTRAINT FK_STORE_ON_STORE_CATEGORY FOREIGN KEY (store_category_id) REFERENCES store_category (id);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_LOCATION FOREIGN KEY (location_id) REFERENCES location (id);

ALTER TABLE user_food
    ADD CONSTRAINT FK_USER_FOOD_ON_FOOD FOREIGN KEY (food_id) REFERENCES food (id);

ALTER TABLE user_food
    ADD CONSTRAINT FK_USER_FOOD_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE user_mission
    ADD CONSTRAINT FK_USER_MISSION_ON_MISSION FOREIGN KEY (mission_id) REFERENCES mission (id);

ALTER TABLE user_mission
    ADD CONSTRAINT FK_USER_MISSION_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE user_term
    ADD CONSTRAINT FK_USER_TERM_ON_TERM FOREIGN KEY (term_id) REFERENCES term (id);

ALTER TABLE user_term
    ADD CONSTRAINT FK_USER_TERM_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);