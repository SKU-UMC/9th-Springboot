package com.example.umc_9th_springboot.domain.user.converter;

import com.example.umc_9th_springboot.domain.user.dto.req.UserReqDTO;
import com.example.umc_9th_springboot.domain.user.entity.User;
import com.example.umc_9th_springboot.global.auth.enums.Role;

import java.time.LocalDate;

public class UserConverter {
    public static User toUser(
            UserReqDTO.SignUpDTO request,
            String encodedPassword,
            Role role
    ) {
        return User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .name(request.getName())
                .gender(request.getGender())
                .birth(LocalDate.parse(request.getBirth()))
                .address(request.getAddress())
                .role(role)
                .point(0)
                .build();
    }
}
