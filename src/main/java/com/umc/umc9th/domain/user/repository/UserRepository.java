package com.umc.umc9th.domain.user.repository;

import com.umc.umc9th.domain.user.dto.response.MyPageResponse;
import com.umc.umc9th.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 마이페이지 화면
    @Query("""
        select new com.umc.umc9th.domain.user.dto.response.MyPageResponse(
            u.id,
            u.userProfileUrl,
            u.userNickname,
            u.userEmail,
            u.userPoint,
            u.isActive,
            u.userSocialProvider,
            u.socialId
        )
        from User u
        where u.id = :userId
    """)
    Optional<MyPageResponse> findMyPageById(@Param("userId") Long userId);
}
