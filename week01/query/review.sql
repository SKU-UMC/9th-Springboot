INSERT INTO review(review_id, store_id, user_id, star, content, create_at, change_at)
VALUES (:reviewId, :storeId, :userId, :star, :content, NOW(6), NULL);
