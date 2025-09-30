INSERT INTO reviews
(store_id, user_id, rating, content, visited_at, created_at, updated_at)
VALUES
    (:storeId, :userId, :rating, :content, :visitedAt, NOW(), NOW());