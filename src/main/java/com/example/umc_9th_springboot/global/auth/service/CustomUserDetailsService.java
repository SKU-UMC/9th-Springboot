package com.example.umc_9th_springboot.global.auth.service;

import com.example.umc_9th_springboot.domain.user.entity.User;
import com.example.umc_9th_springboot.domain.user.repository.UserRepository;
import com.example.umc_9th_springboot.global.auth.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("해당 이메일을 가진 유저가 없습니다: " + email)
                );

        return new CustomUserDetails(user);
    }
}
