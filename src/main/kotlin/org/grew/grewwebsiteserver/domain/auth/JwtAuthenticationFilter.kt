package org.grew.grewwebsiteserver.domain.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.grew.grewwebsiteserver.domain.user.UserService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService, // JWTService를 주입
    private val userService: UserService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authHeader.substring(7)

//        try{
        jwtService.validateToken(token)
//        } catch (e: Exception) {
//            response.status = HttpServletResponse.SC_UNAUTHORIZED
//            response.writer.write("토큰이 유효하지 않습니다.")
//            return
//        }

        // 인증 객체 생성
        val user = userService.getUserByUserId(
            jwtService.getUserId(token)
        ) ?: throw IllegalArgumentException("유효하지 않은 사용자입니다.")

        // 스프링 시큐리티의 GrantedAuthority로 변환
        val authorities = listOf(SimpleGrantedAuthority("ROLE_${user.role}"))

        val authentication = UsernamePasswordAuthenticationToken(
            user, null, authorities
        )

        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        // SecurityContext에 설정
        SecurityContextHolder.getContext().authentication = authentication

        filterChain.doFilter(request, response)
    }
}