package io.api.oauth2;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = parseToken(request);
            if (token != null) {
                String userId = jwtProvider.validate(token);

                if(userId == null) {
                    filterChain.doFilter(request, response);
                    return;
                }
                //fintByUserId로 User의 ID를 가져옴
                UserEntity userEntity = userRepository.findByUserId(userId);
                // User 의 권한을 가져와야함
                String role = userEntity.getUserRole();
                //ROLE_USER, ROLE_ADMIN, ROLE_DEV
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(role));

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null, authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //context에 토큰 값을 담아줌
                securityContext.setAuthentication(authenticationToken);
                //만든 context 등록
                SecurityContextHolder.setContext(securityContext);
            }else { //authorization이 없거나 토큰이 없으면 바로 다음 필터 진행
                filterChain.doFilter(request, response);
                return;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

    private String parseToken(HttpServletRequest request) {
        //헤더에 있는 Authrization을 가져옴
        String bearerToken = request.getHeader("Authorization");
        //가져온 토큰이 널이아니고 Bearer로 시작하면 bearer문자열 값 짜르고 리턴
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }else {
            return null;
        }
    }
}
