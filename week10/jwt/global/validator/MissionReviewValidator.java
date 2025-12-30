package com.example.umc9th1.global.validator;

import com.example.umc9th1.domain.mission.repository.MissionRepository;
import com.example.umc9th1.domain.review.dto.ReviewMoreRequestDto;
import com.example.umc9th1.domain.users.repository.UserMissionRepository;
import com.example.umc9th1.global.annotation.ValidMissionReview;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MissionReviewValidator implements ConstraintValidator<ValidMissionReview, ReviewMoreRequestDto> {

    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;

    @Autowired
    public MissionReviewValidator(MissionRepository missionRepository,
                                  UserMissionRepository userMissionRepository) {
        this.missionRepository = missionRepository;
        this.userMissionRepository = userMissionRepository;
    }

    @Override
    public boolean isValid(ReviewMoreRequestDto dto, ConstraintValidatorContext context) {

        Long missionId = dto.getMissionId();
        Long userId = dto.getUserId();

        if (missionId == null || userId == null) {
            return true; // Controller에서 넣기 전이므로 Validation 건너뜀
        }

        // 미션 존재 여부
        if (!missionRepository.existsById(missionId)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("존재하지 않는 미션입니다.")
                    .addPropertyNode("missionId")
                    .addConstraintViolation();
            return false;
        }

        // 유저가 해당 미션을 도전 중인지 검사
        if (!userMissionRepository.existsByUserIdAndMissionId(userId, missionId)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("해당 미션을 수행 중이 아닙니다.")
                    .addPropertyNode("missionId")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
