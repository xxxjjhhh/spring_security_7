package com.yummi.seven.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // 비밀번호 암호화용 Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티 필터 구획을 내 마음대로 커스텀
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        // 수많은 필터 중 CSRF 필터를 disable 시킴
        http
                .csrf(csrf -> csrf.disable());

        // 로그인 필터 설정
        http
                .formLogin(login -> login
                        .loginProcessingUrl("/login")
                        .loginPage("/login"));

        // 인가 필터에 대한 설정
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/join").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().denyAll()
                );

        // 최종 빌드
        return http.build();
    }






}
