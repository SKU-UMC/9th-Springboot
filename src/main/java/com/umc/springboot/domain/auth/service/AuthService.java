package com.umc.springboot.domain.auth.service;

import com.umc.springboot.domain.auth.exception.AuthErrorCode;
import com.umc.springboot.domain.user.converter.UserConverter;
import com.umc.springboot.domain.user.dto.request.UserRequest;
import com.umc.springboot.domain.user.dto.response.UserResponse;
import com.umc.springboot.domain.user.entity.User;
import com.umc.springboot.domain.user.exception.UserErrorCode;
import com.umc.springboot.domain.user.repository.UserRepository;
import com.umc.springboot.domain.user.service.UserService;
import com.umc.springboot.global.exception.CustomException;
import com.umc.springboot.global.jwt.JwtProvider;
import com.umc.springboot.global.util.RedisUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;
  private final RedisUtil redisUtil;
  private final UserConverter userConverter;
  private final UserService userService;

  private static final String REFRESH_TOKEN_PREFIX = "user:refresh:";

  @Value("${cookie.secure}")
  private boolean secure;

  /**
   * 일반 로그인 처리
   *
   * <p>현재는 "이메일 + 전화번호" 조합으로 인증을 수행한다.
   *
   * @param loginRequest 이메일, 전화번호를 담은 요청 DTO
   * @param response     발급된 액세스/리프레시 토큰을 실어보낼 HttpServletResponse
   * @return 로그인한 사용자 정보
   * @throws CustomException 이메일에 해당하는 유저가 없거나, 전화번호가 일치하지 않는 경우
   *                         {@link AuthErrorCode#INVALID_PASSWORD}
   */
  public UserResponse login(UserRequest.LoginRequest loginRequest, HttpServletResponse response) {
    User user = validateUserCredentials(loginRequest);
    return issueTokensAndSetResponse(user, response);
  }

  /**
   * 테스트용 사용자(예: ID = 1)로 로그인 처리
   *
   * @param response 발급된 액세스/리프레시 토큰을 실어보낼 HttpServletResponse
   * @return 테스트 사용자 정보
   * @throws CustomException 해당 ID의 사용자가 없을 경우 {@link UserErrorCode#USER_NOT_FOUND}
   */
  public UserResponse testLogin(HttpServletResponse response) {
    User user =
        userRepository
            .findById(1L)
            .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    return issueTokensAndSetResponse(user, response);
  }

  /**
   * 로그아웃 처리
   *
   * <p>1) 액세스 토큰을 블랙리스트에 등록<br>
   * 2) Redis 에 저장된 리프레시 토큰 삭제<br> 3) 클라이언트의 refreshToken 쿠키 만료 처리
   *
   * @param request  Authorization 헤더에서 액세스 토큰을 읽기 위한 HttpServletRequest
   * @param response refreshToken 쿠키 삭제를 위한 HttpServletResponse
   * @throws CustomException 액세스 토큰이 없거나 유효하지 않은 경우 {@link AuthErrorCode#INVALID_ACCESS_TOKEN}
   */
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    String accessToken = resolveAccessToken(request);
    if (accessToken == null || !jwtProvider.validateToken(accessToken)) {
      throw new CustomException(AuthErrorCode.INVALID_ACCESS_TOKEN);
    }

    // 1) 액세스 토큰 블랙리스트 등록 (만료 시점까지)
    long expiration =
        jwtProvider.extractExpiration(accessToken).getTime() - System.currentTimeMillis();
    redisUtil.setData("blacklist:" + accessToken, "logout", expiration / 1000);

    // 2) Redis 에서 리프레시 토큰 삭제
    Long userId = jwtProvider.extractUserId(accessToken);
    redisUtil.deleteData(REFRESH_TOKEN_PREFIX + userId);

    // 3) 쿠키에서 refreshToken 제거
    deleteRefreshTokenCookie(response);
  }

  /**
   * 액세스 토큰 재발급
   *
   * <p>1) 쿠키에서 리프레시 토큰을 읽어온 뒤 유효성 검증<br>
   * 2) Redis 에 저장된 리프레시 토큰과 일치하는지 확인<br> 3) 새로운 액세스 토큰을 생성하여 Authorization 헤더에 담아 응답
   *
   * @param request  리프레시 토큰 쿠키 확인용 HttpServletRequest
   * @param response 새 액세스 토큰을 담아보낼 HttpServletResponse
   * @throws CustomException 리프레시 토큰이 없거나, 유효하지 않거나, 저장된 값과 다를 경우
   *                         {@link AuthErrorCode#REFRESH_TOKEN_REQUIRED}
   */
  public void reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
    // 1. 쿠키에서 refreshToken 추출
    String refreshToken = extractRefreshTokenFromCookie(request);
    if (refreshToken == null || !jwtProvider.validateToken(refreshToken)) {
      throw new CustomException(AuthErrorCode.REFRESH_TOKEN_REQUIRED);
    }

    // 2. 사용자 ID 추출
    Long userId = jwtProvider.extractUserId(refreshToken);

    // 3. Redis에 저장된 리프레시 토큰과 비교
    String storedToken = redisUtil.getData(REFRESH_TOKEN_PREFIX + userId);
    if (!refreshToken.equals(storedToken)) {
      throw new CustomException(AuthErrorCode.REFRESH_TOKEN_REQUIRED);
    }

    // 4. 새로운 accessToken 생성 후 응답 헤더에 설정
    String newAccessToken = jwtProvider.createAccessToken(userId);
    setAccessTokenHeader(response, newAccessToken);
  }

  /**
   * 이메일 + 전화번호로 사용자 인증
   */
  private User validateUserCredentials(UserRequest.LoginRequest loginRequest) {
    User user =
        userRepository
            .findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new CustomException(AuthErrorCode.INVALID_PASSWORD));

    if (!loginRequest.getPhone().equals(user.getPhone())) {
      throw new CustomException(AuthErrorCode.INVALID_PASSWORD);
    }

    return user;
  }

  /**
   * 액세스 / 리프레시 토큰 발급 후 응답 헤더·쿠키에 세팅
   */
  private UserResponse issueTokensAndSetResponse(User user, HttpServletResponse response) {
    String accessToken = jwtProvider.createAccessToken(user.getId());
    String refreshToken = jwtProvider.createRefreshToken(user.getId());

    long refreshTokenExpireSeconds = jwtProvider.getRefreshTokenExpireTime() / 1000;
    redisUtil.setData(REFRESH_TOKEN_PREFIX + user.getId(), refreshToken, refreshTokenExpireSeconds);

    setAccessTokenHeader(response, accessToken);
    setRefreshTokenCookie(response, refreshToken, refreshTokenExpireSeconds);

    return userConverter.toResponse(user);
  }

  private void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
    response.setHeader("Authorization", "Bearer " + accessToken);
  }

  /**
   * refreshToken 쿠키 설정
   *
   * <p>로컬 개발 환경(secure=false)과 배포 환경(secure=true)을 분리해서 설정한다.
   */
  private void setRefreshTokenCookie(
      HttpServletResponse response, String refreshToken, long maxAgeSec) {

    ResponseCookie.ResponseCookieBuilder cookie =
        ResponseCookie.from("refreshToken", refreshToken)
            .httpOnly(true)
            .path("/")
            .maxAge(Duration.ofSeconds(maxAgeSec));

    if (secure) {
      // 배포 환경: HTTPS + SameSite=None 옵션만 사용 (도메인은 기본값 사용)
      cookie.secure(true).sameSite("None");
    } else {
      // 로컬 환경
      cookie.secure(false).sameSite("Lax");
    }

    response.addHeader(HttpHeaders.SET_COOKIE, cookie.build().toString());
  }

  private String extractRefreshTokenFromCookie(HttpServletRequest request) {
    if (request.getCookies() == null) {
      return null;
    }

    for (Cookie cookie : request.getCookies()) {
      if ("refreshToken".equals(cookie.getName())) {
        return cookie.getValue();
      }
    }
    return null;
  }

  private String resolveAccessToken(HttpServletRequest request) {
    String bearer = request.getHeader("Authorization");
    if (bearer != null && bearer.startsWith("Bearer ")) {
      return bearer.substring(7);
    }
    return null;
  }

  /**
   * refreshToken 쿠키 제거 (즉시 만료)
   */
  private void deleteRefreshTokenCookie(HttpServletResponse response) {
    ResponseCookie.ResponseCookieBuilder cookie =
        ResponseCookie.from("refreshToken", "")
            .httpOnly(true)
            .path("/")
            .maxAge(Duration.ZERO);

    if (secure) {
      cookie.secure(true).sameSite("None");
    } else {
      cookie.secure(false).sameSite("Lax");
    }

    response.addHeader(HttpHeaders.SET_COOKIE, cookie.build().toString());
  }

  /**
   * 현재 세션(토큰)을 무효화
   *
   * <p>내부적으로 logout()을 호출하되, 예외가 발생해도 삼켜서 다른 트랜잭션에 영향을 주지 않는다.
   */
  public void invalidateCurrentSessionQuietly(
      HttpServletRequest request, HttpServletResponse response) {
    try {
      logout(request, response);
    } catch (CustomException ignore) {
      // 토큰 검증 실패 등 예외가 나더라도 최소한 쿠키는 정리
      deleteRefreshTokenCookie(response);
    }
  }
}
