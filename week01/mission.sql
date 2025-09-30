-- [진행중]

SELECT
    um.id           AS user_mission_id,
    m.reward_points AS points,
    um.state        AS result,
    s.name          AS store_name,
    m.description   AS mission_text
FROM user_missions um
         JOIN missions m ON m.id = um.mission_id
         JOIN stores   s ON s.id = m.store_id
WHERE um.user_id = :userId
  AND um.state = 'IN_PROGRESS'
  AND (:after_id IS NULL OR um.id < :after_id)
ORDER BY um.id DESC
    LIMIT :limit;

-- [진행 완료]

SELECT
    um.id           AS user_mission_id,
    m.reward_points AS points,
    um.state        AS result,
    s.name          AS store_name,
    m.description   AS mission_text
FROM user_missions um
         JOIN missions m ON m.id = um.mission_id
         JOIN stores   s ON s.id = m.store_id
WHERE um.user_id = :userId
  AND um.state = 'COMPLETED'
  AND (:after_id IS NULL OR um.id < :after_id)
ORDER BY um.id DESC
    LIMIT :limit;