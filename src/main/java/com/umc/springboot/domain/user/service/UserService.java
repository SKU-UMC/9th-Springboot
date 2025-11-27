package com.umc.springboot.domain.user.service;

import com.umc.springboot.domain.user.converter.UserConverter;
import com.umc.springboot.domain.user.dto.request.UserRequest;
import com.umc.springboot.domain.user.dto.response.UserResponse;
import com.umc.springboot.domain.user.entity.User;
import com.umc.springboot.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public UserResponse createUser(UserRequest req) {
    User user = UserConverter.toEntity(req);
    userRepository.save(user);
    return UserConverter.toResponse(user);
  }

  public UserResponse getUser(Long id) {
    return userRepository.findById(id)
        .map(UserConverter::toResponse)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
  }
}
