package com.dunsum.backend.common.security;

import com.dunsum.backend.common.security.filters.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    public static final String HEADER_TOKEN = "D-Auth-Token";

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        http.authorizeRequests(
            authorizeRequests -> authorizeRequests
                    .antMatchers("/h2-console/**").permitAll() // 누구나 h2-console 접속 허용
                    .antMatchers(
                            "/dunsum/api/acut/ins/gust",
                            "/dunsum/api/acut/ins/user",
                            "/dunsum/api/otsd/dnf/srvr/sel") // 게스트 로그인 또는 회원가입
                    .permitAll()
                    .anyRequest()
                    .authenticated() // 최소자격 : 로그인
            )
            .cors().disable() // 타 도메인에서 API 호출 가능
            .csrf().disable() // CSRF 토큰 끄기
            .httpBasic().disable() // httpBasic 로그인 방식 끄기
            .formLogin().disable() // 폼 로그인 방식 끄기
            .sessionManagement(sessionManagement ->
                    sessionManagement.sessionCreationPolicy(STATELESS)
            )
            .addFilterBefore(
                    jwtAuthorizationFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
