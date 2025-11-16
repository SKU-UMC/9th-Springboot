package com.umc.umc9th.domain.user.dto.response;

import com.umc.umc9th.domain.user.entity.SocialProvider;

public record MyPageResponse(Long id,
                             String userProfileUrl,
                             String userNickname,
                             String userEmail,
                             Long userPoint,
                             Boolean isActive,
                             SocialProvider userSocialProvider,
                             String socialId) {
}
