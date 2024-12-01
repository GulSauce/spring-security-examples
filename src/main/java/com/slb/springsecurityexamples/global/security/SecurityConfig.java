package com.slb.springsecurityexamples.global.security;

import com.slb.springsecurityexamples.user.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                          CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
                          CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF 설정 (기본 활성화 상태)
            .csrf((csrf) -> csrf.disable())

            // 권한 별 요청 설정
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.GET, "/", "/login", "/register").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/user/register").permitAll()
                    .anyRequest().authenticated()
            )

            // Form Login 설정
            .formLogin(form -> form
                    .loginPage("/login") // 로그인 페이지 URL (기본값: /login)
                    .defaultSuccessUrl("/", true) // 로그인 성공 시 이동할 URL
                    .usernameParameter("username") // 아이디 필드 이름 (기본값: username)
                    .passwordParameter("password") // 비밀번호 필드 이름 (기본값: password)
                    .permitAll() // 로그인 페이지 접근은 누구나 가능
                    .successHandler(customAuthenticationSuccessHandler) // 성공 핸들러 등록
                    .failureHandler(customAuthenticationFailureHandler)  // 로그인 실패 핸들러 설정
            )

            // 로그아웃 설정
            .logout(logout -> logout
                    .logoutUrl("/logout") // 로그아웃 URL (기본값: /logout)
                    .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 URL
                    .invalidateHttpSession(true) // 세션 무효화
                    .deleteCookies("JSESSIONID") // 특정 쿠키 삭제
                    .deleteCookies("USERNAME") // 특정 쿠키 삭제
            )
            // AuthenticationProvider 설정
            .authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder()); // PasswordEncoder 설정

        return authProvider;
    }

    // PasswordEncoder 등록 (BCrypt 사용)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}