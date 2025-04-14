package org.grew.grewwebsiteserver.domain.auth

import org.grew.grewwebsiteserver.domain.user.UserRepository
import org.grew.grewwebsiteserver.domain.user.entity.User
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Login

@Component
class LoginArgumentResolver(
    private val jwtService: JwtService,
    private val userRepository: UserRepository
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        val isLoginAnnotation = parameter.getParameterAnnotation(Login::class.java) != null
        val isUserClass = parameter.parameterType == User::class.java
        return isLoginAnnotation && isUserClass
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        val token = jwtService.getToken(webRequest)
        val userId = jwtService.getUserId(token)

        return userRepository.findByUserId(userId)
            ?: throw IllegalArgumentException("해당 유저는 존재하지 않습니다. userId=$userId")
    }
}