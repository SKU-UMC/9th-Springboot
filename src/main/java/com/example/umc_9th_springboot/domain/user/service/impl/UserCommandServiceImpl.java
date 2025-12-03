package com.example.umc_9th_springboot.domain.user.service.impl;

import com.example.umc_9th_springboot.domain.user.converter.UserConverter;
import com.example.umc_9th_springboot.domain.user.dto.req.UserReqDTO;
import com.example.umc_9th_springboot.domain.user.dto.res.UserResDTO;
import com.example.umc_9th_springboot.domain.user.entity.User;
import com.example.umc_9th_springboot.domain.user.repository.UserRepository;
import com.example.umc_9th_springboot.domain.user.service.UserCommandService;
import com.example.umc_9th_springboot.global.auth.enums.Role;
import com.example.umc_9th_springboot.global.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    //회원가입
    @Override
    public UserResDTO.SignUpDTO signup(UserReqDTO.SignUpDTO dto) {

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        User user = UserConverter.toUser(dto, encodedPassword, Role.USER);

        User savedUser = userRepository.save(user);

        return UserResDTO.SignUpDTO.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .role(savedUser.getRole())
                .build();
    }

    // Session 로그인
    @Override
    public UserResDTO.LoginSessionDTO login(UserReqDTO.LoginSessionDTO dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return UserResDTO.LoginSessionDTO.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    // jwt 로그인
    @Override
    public UserResDTO.LoginJwtDTO loginJwt(UserReqDTO.LoginJwtDTO dto) {

        UserResDTO.LoginSessionDTO loginResult = login(
                new UserReqDTO.LoginSessionDTO(
                        dto.email(),
                        dto.password()
                )
        );

        String accessToken = jwtUtil.createAccessToken(
                loginResult.getUserId(),
                loginResult.getEmail(),
                loginResult.getRole().name()
        );

        return UserResDTO.LoginJwtDTO.builder()
                .userId(loginResult.getUserId())
                .accessToken(accessToken)
                .build();
    }

    // jwt 회원가입
    @Override
    public UserResDTO.SignUpJwtDTO signupJwt(UserReqDTO.SignUpDTO dto) {

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        User user = UserConverter.toUser(dto, encodedPassword, Role.USER);

        User savedUser = userRepository.save(user);


        String accessToken = jwtUtil.createAccessToken(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getRole().name()
        );


        return UserResDTO.SignUpJwtDTO.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .role(savedUser.getRole())
                .accessToken(accessToken)
                .build();
    }

}
