package com.umc.springboot.global.jwt;

import com.umc.springboot.global.security.CustomUserDetails;
import com.umc.springboot.global.util.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;
  private final RedisUtil redisUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {

    String header = request.getHeader("Authorization");

    if (header != null && header.startsWith("Bearer ")) {
      String token = header.substring(7);

      // 1. 블랙리스트 체크 (로그아웃된 토큰인지)
      String blacklisted = redisUtil.getData("blacklist:" + token);
      if (blacklisted != null) {
        log.debug("블랙리스트에 등록된 토큰입니다.");
        filterChain.doFilter(request, response);
        return;
      }

      try {
        // 2. 토큰 유효성 검증
        if (jwtProvider.validateToken(token)) {

          Long userId = jwtProvider.extractUserId(token);

          // 3. userId 기반 CustomUserDetails 생성
          CustomUserDetails userDetails = new CustomUserDetails(userId);

          // 4. Authentication 생성 후 SecurityContext에 저장
          UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(
                  userDetails,
                  null,
                  userDetails.getAuthorities()
              );
          authentication.setDetails(
              new WebAuthenticationDetailsSource().buildDetails(request)
          );

          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      } catch (Exception e) {
        // JwtProvider에서 던지는 CustomException 등은 여기서 로깅만 하고 흘려보냄
        log.debug("JWT 검증 중 예외 발생: {}", e.getMessage());
      }
    }

    // 다음 필터로 진행
    filterChain.doFilter(request, response);
  }

  /**
   * 로그인/회원가입, Swagger 등은 JWT 검증 필터를 타지 않도록 예외 처리
   */
  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getServletPath();
    return path.startsWith("/api/auth")
        || path.startsWith("/swagger-ui")
        || path.startsWith("/v3/api-docs");
  }
}
