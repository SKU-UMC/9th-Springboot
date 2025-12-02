package com.example.umc9th1.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())      // ✅ 최신 문법
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // ✅ 모든 요청 인증 없이 허용
                );

        return http.build();
    }
}
