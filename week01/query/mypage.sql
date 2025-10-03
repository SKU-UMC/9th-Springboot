SELECT  users.name , users.email , users.phone_num, users.point
FROM users
WHERE user_id = :userId;
