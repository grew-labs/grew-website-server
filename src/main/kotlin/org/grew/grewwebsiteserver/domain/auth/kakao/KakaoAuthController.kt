package org.grew.grewwebsiteserver.domain.auth.kakao

import org.grew.grewwebsiteserver.domain.auth.AuthResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/oauth/kakao")
class KakaoAuthController(
    private val kakaoAuthService: KakaoAuthService
) {


    // 카카오 로그인 콜백 (인가 코드 수신 → 토큰 발급 → 사용자 정보 획득)
    @GetMapping("/callback")
    fun kakaoCallback(@RequestParam code: String): ResponseEntity<ApiResponse<AuthResponseDto>> {
        val data = kakaoAuthService.kakaoLogin(code)
        val response = ApiResponse(
            message = "로그인 성공: ${data.nickname}",
            data = data
        )
        return ResponseEntity.ok(response)
    }

}

