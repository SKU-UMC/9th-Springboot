package com.example.umc9th1.domain.users.service;

import com.example.umc9th1.domain.entity.others.Location;
import com.example.umc9th1.domain.entity.others.LocationRepository;
import com.example.umc9th1.domain.enums.GenderType;
import com.example.umc9th1.domain.mission.repository.MissionRepository;
import com.example.umc9th1.domain.users.dto.*;
import com.example.umc9th1.domain.users.entity.Users;
import com.example.umc9th1.domain.users.enums.Role;
import com.example.umc9th1.domain.users.exception.UserException;
import com.example.umc9th1.domain.users.exception.code.UserErrorCode;
import com.example.umc9th1.domain.users.repository.UserMissionRepository;
import com.example.umc9th1.domain.users.repository.UserRepository;
import com.example.umc9th1.global.jwt.JwtTokenProvider;
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

import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMissionRepository userMissionRepository;
    private final MissionRepository  missionRepository;
    private final LocationRepository locationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserMissionRepository userMissionRepository,
                       MissionRepository missionRepository,
                       LocationRepository locationRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.userMissionRepository = userMissionRepository;
        this.missionRepository = missionRepository;
        this.locationRepository = locationRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    //유저Info
    @Transactional(readOnly = true)
    public UserInfoDto getUserInfo(Long userId) {
        //유저 존재 확인
        Users user = userRepository.findById(userId)
                .orElseThrow(()->new UserException(UserErrorCode.USER_NOT_FOUND));
        //userDto값 넣기
        return new UserInfoDto(user.getName(),
                user.getEmail(),
                user.getPhoneNum(),
                user.getPoint()
        );
    }
    //진행중인 미션
    @Transactional(readOnly = true)
    public Page<UserOngoingMissionDto> getUnsuccessfulMissions(Long userId, Pageable pageable) {
        //유저 존재여부 체크
        if (!userRepository.existsById(userId)) {
           throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }
        //리포지토리 메서드 return 페이징 포함
        return userMissionRepository.findUnsuccessfulMissionsByUserId(userId, pageable);
    }
    //진행 완료 미션
    @Transactional(readOnly = true)
    public Page<UserCompletedMissionDto> getSuccessfulMissions(Long userId, Pageable pageable) {
        //유저 존재여부 체크
        if (!userRepository.existsById(userId)) {
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }
        //리포지토리 메서드 return 페이징 포함
        return userMissionRepository.findSuccessfulMissionsByUserId(userId, pageable);
    }

    //유저 홈
    @Transactional(readOnly = true)
    public Page<UserHomeDto> getUserHome(Long userId,Pageable pageable) {
        //유저 존재여부 체크
        if (!userRepository.existsById(userId)) {
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }

        //람다 함수 사용 위한 새로운 result 생성
        Page<UserHomeDto> result =missionRepository.findMissionsByUserId(userId, pageable);

        return result.map(dto -> new UserHomeDto(
                dto.getAddress(),
                dto.getSuccessCount() % 10, //10당 미션 초기화
                dto.getStoreCategory(),
                dto.getMissionId(),
                dto.getStoreName(),
                dto.getConditions(),
                dto.getPoint(),
                dto.getEndDate(),
                dto.getCreateAt()
        ));
    }

    public Long signUp(SignUpRequestDto request) {

        Location location = locationRepository.findById(request.getLocationId())
                .orElseThrow(() -> new UserException(UserErrorCode.LOCATION_NOT_FOUND));

        Users user = new Users(
                request.getName(),
                request.getGender(),
                request.getBirth(),
                request.getEmail(),
                location,
                request.getDetailAddress()
        );

        user.changePassword(passwordEncoder.encode(request.getPassword()));
        user.assignRole(Role.ROLE_USER);

        userRepository.save(user);

        return user.getId();
    }
    public String login(LoginRequestDto request) {

        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserException(UserErrorCode.USER_PASSWORD_ERROR);
        }

        return jwtTokenProvider.createToken(user.getId(), user.getRole().name());
    }
}
