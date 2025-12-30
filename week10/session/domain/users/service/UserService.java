package com.example.umc9th1.domain.users.service;

import com.example.umc9th1.domain.entity.others.Location;
import com.example.umc9th1.domain.entity.others.LocationRepository;
import com.example.umc9th1.domain.mission.repository.MissionRepository;
import com.example.umc9th1.domain.users.dto.*;
import com.example.umc9th1.domain.users.entity.Users;
import com.example.umc9th1.domain.users.enums.Role;
import com.example.umc9th1.domain.users.exception.UserException;
import com.example.umc9th1.domain.users.exception.code.UserErrorCode;
import com.example.umc9th1.domain.users.repository.UserMissionRepository;
import com.example.umc9th1.domain.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMissionRepository userMissionRepository;
    private final MissionRepository  missionRepository;
    private final LocationRepository locationRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserMissionRepository userMissionRepository,
                       MissionRepository missionRepository,
                       LocationRepository locationRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMissionRepository = userMissionRepository;
        this.missionRepository = missionRepository;
        this.locationRepository = locationRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public SignUpResponseDto signUp(SignUpRequestDto request) {

        Location location = locationRepository.findById(request.getLocationId())
                .orElseThrow(() -> new IllegalArgumentException("location not found"));

        Users user = new Users(
                request.getName(),
                null,
                null,
                request.getEmail(),
                location,
                "기본 주소"
        );

        user.changePassword(passwordEncoder.encode(request.getPassword()));
        user.assignRole(Role.ROLE_USER);


        Users saved = userRepository.save(user);

        return SignUpResponseDto.builder()
                .userId(saved.getId())
                .email(saved.getEmail())
                .name(saved.getName())
                .build();
    }
    public void login(LoginRequestDto request, HttpSession session) {

        // 1. 이메일로 유저 조회
        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserException(UserErrorCode.USER_PASSWORD_ERROR);
        }

        // 3. 세션에 유저ID 저장
        session.setAttribute("LOGIN_USER", user.getId());
    }

}
