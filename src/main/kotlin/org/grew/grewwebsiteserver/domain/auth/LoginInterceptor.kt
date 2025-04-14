package org.grew.grewwebsiteserver.domain.auth

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.grew.grewwebsiteserver.domain.user.UserService
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*

@Component
class LoginInterceptor(
    private val jwtService: JwtService,
    private val userService: UserService
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.method == HttpMethod.OPTIONS.toString()) {
            return true
        }

        val authorization = request.getHeader(AUTHORIZATION)
        authorization?.let {
            val token = jwtService.getToken(Optional.of(it))
            jwtService.validateToken(token)
        }

        return true
    }
}