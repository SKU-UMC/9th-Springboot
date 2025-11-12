package com.umc.springboot.global.security;

import com.umc.springboot.domain.auth.exception.AuthErrorCode;
import com.umc.springboot.global.exception.CustomException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

  public static Long getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      throw new CustomException(AuthErrorCode.INVALID_AUTH_CONTEXT);
    }

    Object principal = authentication.getPrincipal();

    if (!(principal instanceof CustomUserDetails userDetails)) {
      throw new CustomException(AuthErrorCode.AUTHENTICATION_NOT_FOUND);
    }

    return userDetails.getUserId();
  }

  public static boolean isAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null && authentication.isAuthenticated();
  }
}
