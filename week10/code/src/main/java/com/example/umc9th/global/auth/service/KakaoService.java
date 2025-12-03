package com.example.umc9th.global.auth.service;

import com.example.umc9th.global.auth.dto.KakaoResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoUri;

    private final RestTemplate restTemplate = new RestTemplate();

    // 1. 인가 코드로 액세스 토큰 요청 (Step 2)
    public String getAccessToken(String code) {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code); // 프론트에서 받은 인가 코드

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<KakaoResDTO.TokenDTO> response = restTemplate.exchange(
                    tokenUri,
                    HttpMethod.POST,
                    kakaoTokenRequest,
                    KakaoResDTO.TokenDTO.class
            );

            return response.getBody().accessToken();
        } catch (Exception e) {
            log.error("카카오 토큰 발급 실패", e);
            throw new RuntimeException("카카오 액세스 토큰을 가져오는데 실패했습니다.");
        }
    }

    // 2. 액세스 토큰으로 사용자 정보 조회 (Step 3)
    public KakaoResDTO.UserInfoDTO getUserInfo(String accessToken) {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);

        try {
            ResponseEntity<KakaoResDTO.UserInfoDTO> response = restTemplate.exchange(
                    userInfoUri,
                    HttpMethod.GET, // or POST
                    kakaoUserInfoRequest,
                    KakaoResDTO.UserInfoDTO.class
            );

            return response.getBody();
        } catch (Exception e) {
            log.error("카카오 사용자 정보 조회 실패", e);
            throw new RuntimeException("카카오 사용자 정보를 가져오는데 실패했습니다.");
        }
    }


}
