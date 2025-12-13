package io.api.oauth2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUserId(String userId);
    Optional<UserEntity> findByUserEmail(String userEmail);
}
