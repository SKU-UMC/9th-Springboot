package com.example.umc9th1.domain.users.service;

import com.example.umc9th1.domain.mission.repository.MissionRepository;
import com.example.umc9th1.domain.users.dto.UserCompletedMissionDto;
import com.example.umc9th1.domain.users.dto.UserHomeDto;
import com.example.umc9th1.domain.users.dto.UserInfoDto;
import com.example.umc9th1.domain.users.dto.UserOngoingMissionDto;
import com.example.umc9th1.domain.users.entity.Users;
import com.example.umc9th1.domain.users.repository.UserMissionRepository;
import com.example.umc9th1.domain.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMissionRepository userMissionRepository;
    private final MissionRepository  missionRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserMissionRepository userMissionRepository,
                       MissionRepository missionRepository) {
        this.userRepository = userRepository;
        this.userMissionRepository = userMissionRepository;
        this.missionRepository = missionRepository;
    }
    //유저Info
    @Transactional(readOnly = true)
    public UserInfoDto getUserInfo(Long userId) {
        //유저 존재 확인
        Users user = userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException(userId + "을 찾을 수 없습니다."));
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
            throw new EntityNotFoundException(userId + "을 찾을 수 없습니다.");
        }
        //리포지토리 메서드 return 페이징 포함
        return userMissionRepository.findUnsuccessfulMissionsByUserId(userId, pageable);
    }
    //진행 완료 미션
    @Transactional(readOnly = true)
    public Page<UserCompletedMissionDto> getSuccessfulMissions(Long userId, Pageable pageable) {
        //유저 존재여부 체크
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(userId + "을 찾을 수 없습니다.");
        }
        //리포지토리 메서드 return 페이징 포함
        return userMissionRepository.findSuccessfulMissionsByUserId(userId, pageable);
    }

    //유저 홈
    @Transactional(readOnly = true)
    public Page<UserHomeDto> getUserHome(Long userId,Pageable pageable) {
        //유저 존재여부 체크
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(userId + "을 찾을 수 없습니다.");
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

}
