package io.api.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        return User.builder()
                .username(userEntity.getUserEmail())
                .password(userEntity.getPassword()) // 암호화된 비밀번호가 저장되어 있어야 함
                .roles(userEntity.getUserRole())  // DB에서 role 가져오기
                .build();
    }
}
