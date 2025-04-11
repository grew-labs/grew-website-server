package org.grew.grewwebsiteserver.domain.auth

import org.grew.grewwebsiteserver.domain.auth.kakao.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val jwtService: JwtService,
) {

    @GetMapping("/refresh")
    fun refreshToken(@RequestParam refreshToken: String): ResponseEntity<ApiResponse<TokenResponse>> {
        val data = jwtService.refreshTokenInfo(refreshToken)
        return ResponseEntity.ok(
            ApiResponse(
                message = "리프레시 성공",
                data = data
            )
        )
    }
}