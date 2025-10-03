SELECT
region.address, --지역 포괄적 주소
(--성공한 미션의 개수를 저장 후에 10개가 되면 초기화 되는 로직은 비즈니스 로직에서 처리
 SELECT COUNT(*)
 FROM user_mission
 WHERE user_mission.user_id = users.user_id
 AND user_mission.success = 1 --성공한 미션
) AS success_count,
abtract_store.abstract_store_name, --추상가게 이름
mission.mission_id, --미션아이디
store.store_name, --가게이름
mission.condition, --미션조건
mission.point, --미션에서 주는 포인트
mission.end_date --미션기한
FROM users --users에서 가져옴
JOIN region ON user.location_id = region.location_id --유저지역과 지역 매핑
JOIN store ON store.location_id = region.location_id --가게지역과 지역 매핑
JOIN abtract_store ON store.abstract_store_id = abtract_store.abstract_store_id --추상가게 매핑
JOIN mission ON mission.store_id = store.store_id -- 미션의가게아이디와 가게의가게아이디 매핑
WHERE users.user_id = :userId --바인드된 userId 정보에서 가져옴
AND ((:last_create_at IS NULL) --페이징 시작 쿼리파라미터로 가져온 데이터가 null일경우 페이징x
OR (mission.create_at < :last_create_at --페이징 시작
OR (mission.create_at = :last_create_at AND mission.mission_id < :last_mission_id)))
ORDER BY mission.create_at DESC, mission.mission_id DESC --최신순이 앞으로 오도록 정렬
LIMIT 5;


