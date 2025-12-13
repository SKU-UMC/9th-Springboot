package io.api.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserProfileController {
    
    private final UserRepository userRepository;
    
    // Bearer 토큰으로만 접근 가능한 프로필 조회
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        // JWT 토큰에서 userId 추출 (JwtAuthenticationFilter에서 설정됨)
        String userId = authentication.getName();
        
        UserEntity user = userRepository.findByUserId(userId);
        
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(Map.of(
                "userId", user.getUserId(),
                "email", user.getUserEmail(),
                "userName", user.getUserName(),
                "role", user.getUserRole()
        ));
    }
}