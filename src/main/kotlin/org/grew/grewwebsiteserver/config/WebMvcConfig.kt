package org.grew.grewwebsiteserver.config

import org.grew.grewwebsiteserver.domain.auth.LoginArgumentResolver
import org.grew.grewwebsiteserver.domain.auth.LoginInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val loginInterceptor: LoginInterceptor,
    private val loginArgumentResolver: LoginArgumentResolver
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(loginInterceptor)
            .addPathPatterns("/**") // 인터셉터를 적용할 경로 설정
            .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**") // 필요시 제외 경로
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(loginArgumentResolver)
    }
}