package io.api.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userInfo) {
        String email = userInfo.get("email");
        String password = userInfo.get("password");
        String userName = userInfo.get("userName");
        
        // 이메일 중복 검사
        if (userRepository.findByUserEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already exists"));
        }
        
        // 사용자 생성
        UserEntity newUser = new UserEntity(
                UUID.randomUUID().toString(),  // userId
                userName,                       // userName
                email,                          // userEmail
                passwordEncoder.encode(password), // password
                "USER"                          // userRole - 기본값 USER
        );
        
        userRepository.save(newUser);
        
        return ResponseEntity.ok(Map.of(
                "message", "User registered successfully",
                "email", email
        ));
    }
    
    // 로그인 (Basic 인증 헤더로 요청 → Bearer 토큰 발급)
    @PostMapping("/login")
    public ResponseEntity<?> login() {
        // Spring Security가 Basic 인증을 이미 처리했으므로
        // SecurityContextHolder에서 인증된 사용자 정보를 가져옴
        Authentication authentication = 
            org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(Map.of(
                "error", "Invalid credentials"
            ));
        }
        
        String email = authentication.getName();
        
        try {
            // 사용자 조회
            UserEntity user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            // JWT 토큰 생성
            String token = jwtProvider.createToken(user.getUserId());
            
            return ResponseEntity.ok(Map.of(
                "token", token,
                "tokenType", "Bearer",
                "userId", user.getUserId(),
                "email", user.getUserEmail()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of(
                "error", "Invalid credentials"
            ));
        }
    }
}