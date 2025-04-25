package org.grew.grewwebsiteserver.config

import org.grew.grewwebsiteserver.domain.auth.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableMethodSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter // JWT 필터 주입
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            // CSRF 설정
            .csrf { it.disable() } // CSRF 비활성화

            // 권한 설정
            .authorizeHttpRequests { auth ->
                auth
                    // 인증 없이 허용할 API 경로
                    .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/oauth/kakao/callback",  // 카카오 로그인 콜백
                        "/auth/**",         // 로그인/회원가입 등 인증 관련 API
                        "/health"               // 헬스체크 등 공개용
                    ).permitAll()

                    .requestMatchers("/api/**").permitAll()

                    .anyRequest().authenticated()
            }

            // JWT 인증 필터 추가
            .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun authenticationManager(configuration: AuthenticationConfiguration) =
        configuration.authenticationManager
}