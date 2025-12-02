package com.example.umc9th1.global.validator;

import com.example.umc9th1.domain.mission.repository.MissionRepository;
import com.example.umc9th1.domain.users.repository.UserMissionRepository;
import com.example.umc9th1.global.annotation.ValidMissionAccess;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RequiredArgsConstructor
public class MissionAccessValidator implements ConstraintValidator<ValidMissionAccess, Long> {

    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;

    @Override
    public boolean isValid(Long missionId, ConstraintValidatorContext context) {

        if (missionId == null) return false;

        Long userId = getUserIdFromPath();
        if (userId == null) return false;

        // 1. 미션 존재 여부
        if (!missionRepository.existsById(missionId)) return false;

        // 2. 유저의 미션인지 확인
        return userMissionRepository
                .findByUserIdAndMissionId(userId, missionId)
                .isPresent();
    }

    private Long getUserIdFromPath() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (requestAttributes == null) return null;

        HttpServletRequest request = requestAttributes.getRequest();

        // URI: /users/5/missions/10/reviews
        // PathVariable userId == 5
        try {
            String path = request.getRequestURI();
            String[] parts = path.split("/");
            return Long.valueOf(parts[2]);   // "/users/{userId}"
        } catch (Exception e) {
            return null;
        }
    }
}
