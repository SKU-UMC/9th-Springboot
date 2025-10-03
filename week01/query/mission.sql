--진행중

SELECT
mission.mission_id, --미션아이디
store.store_name,  --가게이름
mission.point, -- 미션포인트
mission.condition, -- 미션조건
user_mission.start_at -- 미션시작시간
FROM user_mission -- 가져올 곳은 user_mission
JOIN mission ON user_mission.mission_id = mission.mission_id --FK로 되어있는 미션아이디
JOIN store ON mission.store_id = store.store_id --FK로 되어있는 가게아이디 <n+1문제 예방>
WHERE user_mission.user_id = :userId --userId 바인드
AND user_mission.success = 0 -- 성공하지못한 칼럼 가져오기
--가장 오래된 순 앞으로 페이징
AND ((:last_create_at IS NULL) --첫페이지 일경우 페이징X
--last_create_at , lastmission_at은 쿼리파라미터로 가져옴
--이전페이지의 생성일자보다 나중에 생성된 mission.create_at을 가져옴
OR(mission.create_at > :last_create_at
--만약 datetime이 같을 경우 mission_id가 마지막 미션보다 큰값을 가져옴
OR (mission.create_at = :last_create_at
AND mission.mission_id > :last_mission_id)))
ORDER BY mission.create_at ASC, mission.mission_id ASC --오래된 순이 앞으로 오도록 정렬
LIMIT 5;

------------------------------------------------------------------------------------------------------

--진행완료

SELECT
mission.mission_id,
store.store_name,
mission.point,
mission.condition,
user_mission.start_at
FROM user_mission
JOIN mission ON user_mission.mission_id = mission.mission_id
JOIN store ON mission.store_id = store.store_id
WHERE user_mission.user_id = :userId
AND user_mission.success = 1 --진행중과의 차이점 1 => true
--가장 최근 순 앞으로 페이징
AND ((:last_create_at IS NULL)
OR (mission.create_at < :last_create_at
OR (mission.create_at = :last_create_at AND mission.mission_id < :last_mission_id)))
ORDER BY mission.create_at DESC, mission.mission_id DESC --최신순이 앞으로 오도록 정렬
LIMIT 5;
