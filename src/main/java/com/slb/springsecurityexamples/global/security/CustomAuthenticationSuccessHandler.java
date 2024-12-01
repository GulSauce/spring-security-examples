package com.slb.springsecurityexamples.global.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 인증된 사용자의 이름을 가져오기
        String username = authentication.getName();

        // 쿠키 생성
        Cookie usernameCookie = new Cookie("USERNAME", username);
        usernameCookie.setPath("/"); // 애플리케이션 전체에서 사용 가능
        usernameCookie.setMaxAge(-1); // 7일 동안 유효
        usernameCookie.setHttpOnly(false); // 클라이언트 스크립트에서 접근 가능
        response.addCookie(usernameCookie);

    }
}