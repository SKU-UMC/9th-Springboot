package com.example.umc9th.domain.user.service;

import com.example.umc9th.domain.user.repository.UserRepository;
import com.example.umc9th.global.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // 유저 정보 저장/업데이트
    public Users saveUser(Users user) {
        return userRepository.save(user);
    }
    // 유저 조회
    public Optional<Users> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
