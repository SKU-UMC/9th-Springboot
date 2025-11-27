package com.umc.springboot.domain.user.converter;

import com.umc.springboot.domain.user.dto.request.UserRequest;
import com.umc.springboot.domain.user.dto.response.UserResponse;
import com.umc.springboot.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

  public static User toEntity(UserRequest req) {
    return User.builder()
        .name(req.getName())
        .gender(req.getGender())
        .birth(req.getBirth())
        .address(req.getAddress())
        .email(req.getEmail())
        .phone(req.getPhone())
        .build();
  }

  public static UserResponse toResponse(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .gender(user.getGender())
        .birth(user.getBirth())
        .email(user.getEmail())
        .phone(user.getPhone())
        .address(user.getAddress())
        .build();
  }
}
