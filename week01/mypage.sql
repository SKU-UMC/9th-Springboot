SELECT
    u.id,
    u.nickname,
    u.email,
    u.phone,
    u.phone_verified,
    u.profile_img,
    COALESCE(SUM(p.amount), 0) AS point_balance
FROM users u
         LEFT JOIN point_tx p ON p.user_id = u.id
WHERE u.id = :userId
  AND u.deleted_at IS NULL
GROUP BY u.id, u.nickname, u.email, u.phone, u.phone_verified, u.profile_img
    LIMIT 1;