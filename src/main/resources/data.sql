-- 지역
INSERT INTO region (name, mission_reward_count)
VALUES
    ('정릉동', 0),
    ('쌍문동', 0);

-- 가게
INSERT INTO shop (name, address, owner_code, region_id)
VALUES
    ('반이학생마라탕마라반', '정릉로 136', 1, 1),
    ('홍콩반점0410',         '정릉로 120', 2, 1),
    ('이디야커피 서경대점',  '서경대로 12', 3, 1),
    ('죠스떡볶이 정릉점',    '정릉로 88',  4, 1);

-- foodType
INSERT INTO food_type (name)
VALUES
    ('중식'),
    ('커피/디저트'),
    ('분식'),
    ('한식');

-- 유저
INSERT INTO user (name, gender, birth, address, status, point)
VALUES
    ('최예윤', 'F', '2003-04-23', '정릉동', 'ACTIVE', 120),
    ('마루', 'M', '2018-02-10', '정릉동', 'ACTIVE', 50),
    ('홍길동', 'M', '1995-07-12', '쌍문동', 'ACTIVE', 300),
    ('김철수', 'M', '1998-09-01', '정릉동', 'ACTIVE', 200);

-- 유저 취향 음식
INSERT INTO user_food (user_id, food_type_id)
VALUES
    (1, 1),
    (2, 4),
    (3, 1),
    (4, 3);

INSERT INTO user_region (user_id, region_id)
VALUES
    (1, 1),
    (2, 1),
    (3, 2),
    (4, 1);


-- Review 더미 데이터
-- 가게: 반이학생마라탕마라반
INSERT INTO review (content, rating, shop_id, user_id)
VALUES
    ('국물 진하고 맛있어요!', 5.0, 1, 1),
    ('양이 많고 만족스러워요.', 4.2, 1, 3),
    ('조금 짰지만 괜찮았어요.', 3.5, 1, 4),
    ('너무 매워요 ㅠㅠ', 2.0, 1, 2);

-- 가게: 홍콩반점0410
INSERT INTO review (content, rating, shop_id, user_id)
VALUES
    ('짬뽕 국물이 끝내줘요!', 5.0, 2, (SELECT id FROM user WHERE name='홍길동')),
    ('탕수육 맛있어요.',       4.5, 2, (SELECT id FROM user WHERE name='최예윤')),
    ('면이 퍼져 있었어요.',   3.3, 2, (SELECT id FROM user WHERE name='마루')),
    ('짬뽕이 너무 짜요.',     2.5, 2, (SELECT id FROM user WHERE name='김철수'));

-- 가게: 이디야커피 서경대점
INSERT INTO review (content, rating, shop_id, user_id)
VALUES
    ('커피 향이 좋아요 ', 5.0, 3, 1),
    ('조용하고 공부하기 좋아요.', 4.7, 3, 2),
    ('자리 부족해요.', 3.9, 3, 3);


-- 가게: 죠스떡볶이 정릉점
INSERT INTO review (content, rating, shop_id, user_id)
VALUES
    ('떡이 쫄깃쫄깃해요!', 5.0, 4, 2),
    ('어묵 국물 맛있어요.', 4.0, 4, 1),
    ('매운데 맛있어요.', 4.3, 4, 3),
    ('양이 너무 적어요.', 2.7, 4, 4);
